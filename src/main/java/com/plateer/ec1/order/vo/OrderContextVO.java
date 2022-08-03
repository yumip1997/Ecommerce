package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.validator.OrderValidator;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderContextVO {

    private final AfterStrategy afterStrategy;
    private final OrderValidator orderValidator;

}
