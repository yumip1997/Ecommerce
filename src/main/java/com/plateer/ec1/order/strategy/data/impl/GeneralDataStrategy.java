package com.plateer.ec1.order.strategy.data.impl;

import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.OrderDto;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.OrderRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class GeneralDataStrategy implements DataStrategy {

    @Override
    public OrderDto create(OrderRequest orderRequest, OrderProductView orderProductView) {
        log.info("일반상품 주문 데이터를 생성한다.");
        return null;
    }

    @Override
    public OrderType getType() {
        return OrderType.GENERAL;
    }
}
