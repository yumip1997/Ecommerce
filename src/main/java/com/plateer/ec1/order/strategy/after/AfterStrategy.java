package com.plateer.ec1.order.strategy.after;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.vo.OrderDto;
import com.plateer.ec1.order.vo.OrderRequest;

public interface AfterStrategy extends CustomFactory<SystemType> {

    void call(OrderRequest orderRequest, OrderDto orderDto);

}
