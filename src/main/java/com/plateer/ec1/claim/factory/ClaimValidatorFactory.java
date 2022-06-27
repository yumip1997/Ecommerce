package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ValidatorType;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.common.factory.FactoryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClaimValidatorFactory extends FactoryTemplate<ValidatorType, ClaimValidator> {

    public ClaimValidatorFactory(List<ClaimValidator> list) {
        super(list);
    }
}


