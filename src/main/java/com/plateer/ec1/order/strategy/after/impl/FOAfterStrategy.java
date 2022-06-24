package com.plateer.ec1.order.strategy.after.impl;

import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.vo.OrderDto;
import com.plateer.ec1.order.vo.OrderRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class FOAfterStrategy implements AfterStrategy {

    @Override
    public void call(OrderRequest orderRequest, OrderDto orderDto) {
        log.info("FO 주문 후처리");
    }

    @Override
    public SystemType getType() {
        return SystemType.FO;
    }
}
