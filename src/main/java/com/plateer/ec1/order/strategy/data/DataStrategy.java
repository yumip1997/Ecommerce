package com.plateer.ec1.order.strategy.data;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.OrderProductViewVO;
import com.plateer.ec1.order.vo.OrderRequestVO;

public interface DataStrategy extends CustomFactory<OrderType> {

    OrderVO create(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO);

}
