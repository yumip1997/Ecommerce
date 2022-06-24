package com.plateer.ec1.order.service;

import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.factory.AfterStrategyFactory;
import com.plateer.ec1.order.factory.DataStrategyFactory;
import com.plateer.ec1.order.mapper.OrderDao;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.OrderRequestVO;
import com.plateer.ec1.payment.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final DataStrategyFactory dataStrategyFactory;
    private final AfterStrategyFactory afterStrategyFactory;

    private final OrderHistoryService orderHistoryService;
    private final OrderDao orderDao;
    private final PayService payService;

    public void order(OrderRequestVO orderRequestVO) throws Exception {
        OrderType orderType = OrderType.findOrderType(orderRequestVO.getOrderType());
        DataStrategy dataStrategy = dataStrategyFactory.get(orderType);

        SystemType systemType = SystemType.findSystemType(orderRequestVO.getSystemType());
        AfterStrategy afterStrategy = afterStrategyFactory.get(systemType);

        OrderContext orderContext = new OrderContext(orderHistoryService, orderDao, payService);
        orderContext.execute(dataStrategy, afterStrategy, orderRequestVO);
    }

}
