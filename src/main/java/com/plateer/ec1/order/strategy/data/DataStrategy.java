package com.plateer.ec1.order.strategy.data;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.OrderProductViewVO;
import com.plateer.ec1.order.vo.OrderRequestVO;

public interface DataStrategy extends CustomFactory<OPT0001Code> {

    OrderVO create(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO);

}
