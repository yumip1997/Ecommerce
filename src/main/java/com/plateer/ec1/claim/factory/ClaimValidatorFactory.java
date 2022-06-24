package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimValidatorType;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.common.factory.FactoryTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClaimValidatorFactory extends FactoryTemplate<ClaimValidatorType, ClaimValidator> {

    public ClaimValidatorFactory(List<ClaimValidator> list) {
        super(list);
    }
}


