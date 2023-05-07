package com.plateer.ec1.claim.validation.validator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimValidationVO;
import com.plateer.ec1.common.factory.StrategyTypes;
import org.springframework.util.CollectionUtils;

import java.util.List;

public interface ClaimValidator extends StrategyTypes<ClaimBusiness> {

    void validate(ClaimValidationVO validationVO);

    static void validateAll(ClaimValidationVO validationVO, List<ClaimValidator> claimValidatorList){
        if(CollectionUtils.isEmpty(claimValidatorList)) return;

        for (ClaimValidator claimValidator : claimValidatorList) {
            claimValidator.validate(validationVO);
        }
    }

}
