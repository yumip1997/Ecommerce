package com.plateer.ec1.order.strategy.data;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;

import java.util.List;

public interface DataStrategy extends CustomFactory<OPT0001Code> {

    OrderVO create(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList);

}
