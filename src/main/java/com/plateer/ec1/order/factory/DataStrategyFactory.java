package com.plateer.ec1.order.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataStrategyFactory extends FactoryTemplate<OrderType, DataStrategy> {

    public DataStrategyFactory(List<DataStrategy> list) {
        super(list);
    }
}
