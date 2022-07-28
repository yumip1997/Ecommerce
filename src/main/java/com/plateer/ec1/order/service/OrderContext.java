package com.plateer.ec1.order.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.common.aop.mnt.annotation.OrdClmMntLog;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.factory.AfterStrategyFactory;
import com.plateer.ec1.order.factory.DataStrategyFactory;
import com.plateer.ec1.order.mapper.OrdTrxMapper;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.*;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.product.service.ProductInfoService;
import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderContext {

    private final PayService payService;
    private final ProductInfoService productInfoService;
    private final OrdTrxMapper ordTrxMapper;

    @LogTrace @OrdClmMntLog
    @Transactional
    public OrdClmCreationVO<OrderVO, Object> doOrderProcess(OrderContextVO orderContextVO){
        OrderProductViewVO ordProductViewVO = getOrdProductViewVO(orderContextVO.getOrderRequestVO());

        validate(orderContextVO.getOrderValidator(), ordProductViewVO);

        OrderVO orderVO= createData(orderContextVO, ordProductViewVO);

        //TODO 주문 데이터 등록

        payService.approve(orderVO.toPayApproveReqVO());

        return orderVO.toOrdClmCreationVO();
    }

    public void doOrderAfterProcess(OrderContextVO orderContextVO, OrderVO orderVO) {
        AfterStrategy afterStrategy = orderContextVO.getAfterStrategy();
        afterStrategy.call(orderContextVO.getOrderRequestVO(), orderVO);
    }

    private OrderProductViewVO getOrdProductViewVO(OrderRequestVO orderRequestVO){
        List<ProductInfoVO> param = convertProductInfoVOList(orderRequestVO.getOrderProductVOList());

        List<ProductInfoVO> productInfoVOList = productInfoService.getProductInfoVo(param);
        Map<String, ProductInfoVO> productInfoVOMap = convertProductInfoVOMap(productInfoVOList);

        return new OrderProductViewVO(orderRequestVO, productInfoVOList, productInfoVOMap);
    }

    private List<ProductInfoVO> convertProductInfoVOList(List<OrderProductVO> productVOList){
        return productVOList.stream()
                .map(OrderProductBaseVO::toProductInfoVO)
                .collect(Collectors.toList());
    }

    private Map<String, ProductInfoVO> convertProductInfoVOMap(List<ProductInfoVO> productInfoVOList){
        return productInfoVOList.stream()
                .collect(Collectors.toMap(ProductInfoVO::getGoodNoItemNo, Function.identity()));
    }

    private void validate(OrderValidator orderValidator, OrderProductViewVO orderProductViewVO){
        boolean isValid = orderValidator.test(orderProductViewVO);

        if(isValid) return;
        throw new BusinessException(OrderException.INVALID_ORDER.msg);
    }

    private OrderVO createData(OrderContextVO orderContextVO, OrderProductViewVO orderProductViewVO){
        DataStrategy dataStrategy = orderContextVO.getDataStrategy();
        return dataStrategy.create(orderContextVO.getOrderRequestVO(), orderProductViewVO);
    }

}
