package com.plateer.ec1.order.strategy.after;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.OrderRequestVO;

public interface AfterStrategy extends CustomFactory<SystemType> {

    void call(OrderRequestVO orderRequestVO, OrderVO orderVO);

}
