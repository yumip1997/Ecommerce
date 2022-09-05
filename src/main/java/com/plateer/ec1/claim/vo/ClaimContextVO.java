package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.claim.validation.verifier.AmountVerifier;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ClaimContextVO {

    private ClaimBusiness claimBusiness;
    private List<ClaimValidator> validatorList;
    private List<AmountVerifier> verifierList;

}
