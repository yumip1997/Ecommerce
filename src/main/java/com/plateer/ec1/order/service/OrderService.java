package com.plateer.ec1.order.service;

import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.factory.AfterStrategyFactory;
import com.plateer.ec1.order.factory.DataStrategyFactory;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.OrderContextVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.order.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class OrderService {

    private final DataStrategyFactory dataStrategyFactory;
    private final AfterStrategyFactory afterStrategyFactory;
    private final OrderContext orderContext;

    public void order(@Valid OrderRequestVO orderRequestVO){
        try{
            OrderContextVO orderContextVO = getOrderContextVO(orderRequestVO);

            OrdClmCreationVO<OrderVO, Object> creationVO = orderContext.doOrderProcess(orderContextVO);
            orderContext.doOrderAfterProcess(orderContextVO, creationVO.getInsertData());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private OrderContextVO getOrderContextVO(OrderRequestVO orderRequestVO){
        return OrderContextVO.builder()
                .orderRequestVO(orderRequestVO)
                .orderValidator(getOrderValidator(orderRequestVO))
                .dataStrategy(getDataStrategy(orderRequestVO.getOrderType()))
                .afterStrategy(getAfterStrategy(orderRequestVO.getSystemType()))
                .build();
    }

    private OrderValidator getOrderValidator(OrderRequestVO orderRequestVO){
        return OrderValidator.findOrderValidator(orderRequestVO);
    }

    private DataStrategy getDataStrategy(String typeStr){
        OPT0001Code OPT0001Code = com.plateer.ec1.order.enums.OPT0001Code.findOrderType(typeStr);
        return dataStrategyFactory.get(OPT0001Code);
    }

    private AfterStrategy getAfterStrategy(String typeStr){
        SystemType systemType = SystemType.findSystemType(typeStr);
        return afterStrategyFactory.get(systemType);
    }

}
