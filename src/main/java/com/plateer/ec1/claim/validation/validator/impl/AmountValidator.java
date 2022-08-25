package com.plateer.ec1.claim.validation.validator.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimValidationVO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

public interface AmountValidator extends ClaimValidator {

    @Component
    class CompleteAmountValidator implements AmountValidator{
        @Override
        public void validate(ClaimValidationVO validationVO) {

        }

        @Override
        public List<ClaimBusiness> getTypes() {
            return Arrays.asList(GCC, MCC);
        }
    }

    @Component
    class AcceptWithdrawalAmountValidator implements AmountValidator{

        @Override
        public void validate(ClaimValidationVO validationVO) {

        }

        @Override
        public List<ClaimBusiness> getTypes() {
            return Arrays.asList(MCA, GRA, GRW, GEA, GEW);
        }
    }
}
