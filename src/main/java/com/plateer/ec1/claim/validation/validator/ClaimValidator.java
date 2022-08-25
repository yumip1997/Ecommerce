package com.plateer.ec1.claim.validation.validator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimValidationVO;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;

public interface ClaimValidator extends MultiValueCustomFactory<ClaimBusiness> {

    void validate(ClaimValidationVO validationVO);

}
