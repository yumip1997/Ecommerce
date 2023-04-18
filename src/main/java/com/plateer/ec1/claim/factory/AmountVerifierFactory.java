package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.verifier.AmountVerifier;
import com.plateer.ec1.common.factory.AbstractFactoryMultiValue;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AmountVerifierFactory extends AbstractFactoryMultiValue<ClaimBusiness, AmountVerifier> {

    public AmountVerifierFactory(List<AmountVerifier> list) {
        super(list);
    }

}
