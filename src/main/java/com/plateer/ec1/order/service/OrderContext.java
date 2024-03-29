package com.plateer.ec1.order.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.order.creator.OrderDataCreator;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.manipulator.OrderDataManipulator;
import com.plateer.ec1.order.mapper.OrderMapper;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.OrderContextVO;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.promotion.cupusecnl.service.CupUseCnlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderContext {

    private final OrderMapper orderMapper;
    private final OrdClmMntService ordClmMntService;
    private final PayService payService;
    private final CupUseCnlService cupUseCnlService;
    private final OrderDataManipulator orderDataManipulator;

    @LogTrace
    @Transactional
    public void order(OrderRequestVO orderRequestVO, OrderContextVO orderContextVO){
        Long logSeq =  ordClmMntService.insertOrderHistory(orderRequestVO);
        OrdClmCreationVO<OrderVO,Object> creationVO = new OrdClmCreationVO<>();

        try{
            List<OrderProductView> orderProductViewList = getOrdProductView(orderRequestVO);

            validate(orderContextVO.getOrderValidator(), orderRequestVO, orderProductViewList);

            creationVO = createData(orderRequestVO, orderProductViewList);

            orderDataManipulator.insertOrder(creationVO.getInsertData());

            callExternalIF(orderRequestVO);

            validateAmount(orderRequestVO.getOrdNo());
        }catch (Exception e){
            creationVO.setException(e);
            throw e;
        }finally {
            ordClmMntService.updateOrderHistory(logSeq, creationVO);
        }
    }

    private List<OrderProductView> getOrdProductView(OrderRequestVO orderRequestVO) {
        return orderMapper.getOrderProductView(orderRequestVO.getOrderProductVOList());
    }

    private void validate(OrderValidator orderValidator, OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        orderValidator.isValid(orderRequestVO, orderProductViewList);
    }

    private OrdClmCreationVO<OrderVO, Object> createData(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        OrderDataCreator orderDataCreator = new OrderDataCreator(orderMapper);
        return orderDataCreator.create(orderRequestVO, orderProductViewList);
    }

    private void callExternalIF(OrderRequestVO orderRequestVO) {
        payService.approve(orderRequestVO.toApproveReqVO());
        cupUseCnlService.useCup(orderRequestVO.toCupIssVOList());
    }

    private void validateAmount(String ordNo) {
        Boolean isValid = orderMapper.validateAmount(ordNo);
        if(isValid == null || !isValid){
            throw new BusinessException(OrderException.INVALID_AMT.msg);
        }
    }
}
