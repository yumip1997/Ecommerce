package com.plateer.ec1.claim.validation.validator.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimValidationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ClaimInfoValidator implements ClaimValidator {

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(ClaimBusiness.values());
    }

    @Override
    public void validate(ClaimValidationVO validationVO) {
        validationVO.isNotEmptyClaimGoodsInfo();
        validationVO.isValidProductType();
        validationVO.isValidOrdPrgsScd();
    }

}
