package com.plateer.ec1.order.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.common.aop.mnt.annotation.OrdClmMntLog;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.factory.AfterStrategyFactory;
import com.plateer.ec1.order.factory.DataStrategyFactory;
import com.plateer.ec1.order.mapper.OrdTrxMapper;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.*;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.product.service.ProductInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderContext {
    private final DataStrategyFactory dataStrategyFactory;
    private final AfterStrategyFactory afterStrategyFactory;

    private final PayService payService;
    private final ProductInfoService productInfoService;
    private final OrdTrxMapper ordTrxMapper;

    @LogTrace @OrdClmMntLog
    @Transactional
    public OrdClmCreationVO<OrderVO, Object> doOrderProcess(OrderRequestVO orderRequestVO){
        //주문상품조회
        OrderProductViewVO productView = new OrderProductViewVO();
        //유효성검사
        validate(orderRequestVO);
        //주문데이터생성
        OrderVO orderVO = create(orderRequestVO, productView);
        //주문데이터등록
        //결제호출
        payService.approve(orderVO.toPayApproveReqVO());
        return orderVO.toOrdClmCreationVO();
    }

    public void doOrderAfterProcess(OrderRequestVO orderRequestVO, OrderVO orderVO) {
        AfterStrategy afterStrategy = getAfterStrategy(orderRequestVO.getSystemType());
        afterStrategy.call(orderRequestVO, orderVO);
    }

    private void validate(OrderRequestVO orderRequestVO){
        OrderValidator orderValidator = OrderValidator.findOrderValidator(orderRequestVO);
        boolean isValid = orderValidator.test(new OrderValidationVO());

        if(isValid) return;
        throw new BusinessException(OrderException.INVALID_ORDER.msg);
    }

    private OrderVO create(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO){
        DataStrategy dataStrategy = getDataStrategy(orderRequestVO.getOrderType());
        return dataStrategy.create(orderRequestVO, orderProductViewVO);
    }

    private DataStrategy getDataStrategy(String typeStr){
        OrderType orderType = OrderType.findOrderType(typeStr);
        return dataStrategyFactory.get(orderType);
    }

    private AfterStrategy getAfterStrategy(String typeStr){
        SystemType systemType = SystemType.findSystemType(typeStr);
        return afterStrategyFactory.get(systemType);
    }
}
