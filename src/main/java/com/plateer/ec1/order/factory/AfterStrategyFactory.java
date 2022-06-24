package com.plateer.ec1.order.factory;

import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AfterStrategyFactory {

    private final Map<SystemType, AfterStrategy> map = new HashMap<>();

    public AfterStrategyFactory(List<AfterStrategy> afterStrategyList){
        afterStrategyList.forEach(afterStrategy -> map.put(afterStrategy.getType(), afterStrategy));
    }

    public AfterStrategy getAfterStrategy(SystemType systemType){
        return map.get(systemType);
    }

}
