package com.plateer.ec1.order.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AfterStrategyFactory extends FactoryTemplate<SystemType, AfterStrategy> {

    public AfterStrategyFactory(List<AfterStrategy> list) {
        super(list);
    }
}
