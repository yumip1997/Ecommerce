package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import com.plateer.ec1.common.factory.FactoryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClaimAfterStrategyFactory extends FactoryTemplate<ClaimStatusType, ClaimAfterStrategy> {

    public ClaimAfterStrategyFactory(List<ClaimAfterStrategy> list) {
        super(list);
    }

}
