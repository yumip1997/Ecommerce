package com.plateer.ec1.order.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataStrategyFactory extends FactoryTemplate<OPT0001Code, DataStrategy> {

    public DataStrategyFactory(List<DataStrategy> list) {
        super(list);
    }
}
