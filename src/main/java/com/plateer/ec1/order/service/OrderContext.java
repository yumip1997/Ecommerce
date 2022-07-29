package com.plateer.ec1.order.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.common.aop.mnt.annotation.OrdClmMntLog;
import com.plateer.ec1.order.manipulator.OrderDataManipulator;
import com.plateer.ec1.order.mapper.OrderMapper;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.OrderContextVO;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.payment.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderContext {

    private final PayService payService;
    private final OrderMapper orderMapper;
    private final OrderDataManipulator orderDataManipulator;

    @LogTrace @OrdClmMntLog
    @Transactional
    public OrdClmCreationVO<OrderVO, Object> doOrderProcess(OrderContextVO orderContextVO){
        List<OrderProductView> orderProductViewList = getOrdProductView(orderContextVO.getOrderRequestVO());

        validate(orderContextVO.getOrderValidator(), orderContextVO.getOrderRequestVO(), orderProductViewList);

        OrderVO orderVO= createData(orderContextVO.getDataStrategy(), orderContextVO.getOrderRequestVO(), orderProductViewList);

        orderDataManipulator.insertOrder(orderVO);

        payService.approve(orderVO.toPayApproveReqVO());

        return orderVO.toOrdClmCreationVO();
    }

    public void doOrderAfterProcess(OrderContextVO orderContextVO, OrderVO orderVO) {
        AfterStrategy afterStrategy = orderContextVO.getAfterStrategy();
        afterStrategy.call(orderContextVO.getOrderRequestVO(), orderVO);
    }

    private List<OrderProductView> getOrdProductView(OrderRequestVO orderRequestVO){
        return orderMapper.getOrderProductView(orderRequestVO.getOrderProductVOList());
    }

    private void validate(OrderValidator orderValidator, OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList){
       orderValidator.isValid(orderRequestVO, orderProductViewList);
    }

    private OrderVO createData(DataStrategy dataStrategy, OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList){
        return dataStrategy.create(orderRequestVO, orderProductViewList);
    }

}
