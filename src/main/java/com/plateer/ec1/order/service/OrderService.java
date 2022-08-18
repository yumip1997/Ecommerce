package com.plateer.ec1.order.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderContextVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class OrderService {

    private final OrderContext orderContext;

    @LogTrace
    @Transactional
    public void order(@Valid OrderRequestVO orderRequestVO){
        OrderContextVO orderContextVO = getOrderContextVO(orderRequestVO.getOrderBasicVO());
        orderContext.order(orderRequestVO, orderContextVO);
    }

    private OrderContextVO getOrderContextVO(OrderBasicVO orderBasicVO){
        return OrderContextVO.builder()
                .orderValidator(getOrderValidator(orderBasicVO.getOrdSysCcd(), orderBasicVO.getOrdTpCd()))
                .build();
    }

    private OrderValidator getOrderValidator(String systemType, String orderType){
        return OrderValidator.findOrderValidator(systemType, orderType);
    }
}
