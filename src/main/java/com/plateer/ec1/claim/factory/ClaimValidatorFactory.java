package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.common.factory.AbstractFactoryMultiValue;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClaimValidatorFactory extends AbstractFactoryMultiValue<ClaimBusiness, ClaimValidator> {

    public ClaimValidatorFactory(List<ClaimValidator> list) {
        super(list);
    }

}
