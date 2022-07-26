package com.plateer.ec1.order.service;

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
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.OrderProductViewVO;
import com.plateer.ec1.order.vo.OrderRequestVO;
import com.plateer.ec1.order.vo.OrderValidationVO;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.product.service.ProductService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;



@RequiredArgsConstructor
@Component
@Slf4j
public class OrderContext {
    private final DataStrategyFactory dataStrategyFactory;
    private final AfterStrategyFactory afterStrategyFactory;

    private final PayService payService;
    private final ProductService productService;
    private final OrdTrxMapper ordTrxMapper;

    public void execute(OrderRequestVO orderRequestVO){
        try{
            OrderVO orderVO = doOrderProcess(orderRequestVO);
            doOrderAfterProcess(orderRequestVO, orderVO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private OrderVO doOrderProcess(OrderRequestVO orderRequestVO){
        //주문상품조회
        OrderProductViewVO productView = new OrderProductViewVO();
        //유효성검사
        validate(orderRequestVO);
        //주문데이터생성
        OrderVO orderVO = create(orderRequestVO, productView);
        //주문데이터등록
        //결제호출
        payService.approve(orderVO.toPayApproveReqVO());
        return orderVO;
    }

    private void doOrderAfterProcess(OrderRequestVO orderRequestVO, OrderVO orderVO) {
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
