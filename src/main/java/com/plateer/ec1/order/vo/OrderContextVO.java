package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderContextVO {

    private OrderRequestVO orderRequestVO;
    private DataStrategy dataStrategy;
    private AfterStrategy afterStrategy;
    private OrderValidator orderValidator;

}
