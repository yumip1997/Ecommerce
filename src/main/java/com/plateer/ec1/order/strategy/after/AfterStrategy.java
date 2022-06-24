package com.plateer.ec1.order.strategy.after;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.vo.OrderDto;
import com.plateer.ec1.order.vo.OrderRequest;

public interface AfterStrategy extends FactoryTemplate<SystemType> {

    void call(OrderRequest orderRequest, OrderDto orderDto);

}
