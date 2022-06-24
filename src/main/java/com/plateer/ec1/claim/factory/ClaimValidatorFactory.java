package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimValidatorType;
import com.plateer.ec1.claim.validator.ClaimValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClaimValidatorFactory {

    private final Map<ClaimValidatorType, ClaimValidator> map = new HashMap<>();

    public ClaimValidatorFactory(List<ClaimValidator> claimValidatorList){
        claimValidatorList.forEach(claimValidator -> map.put(claimValidator.getType(), claimValidator));
    }

    public ClaimValidator getClaimValidator(ClaimValidatorType claimValidatorType){
        return map.get(claimValidatorType);
    }
}


