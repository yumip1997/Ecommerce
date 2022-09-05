package com.plateer.ec1.claim.validation.verifier;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimValidationVO;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

public interface AmountVerifier extends MultiValueCustomFactory<ClaimBusiness> {

    void verifyAmount(Long reqCnlAmt, OrderClaimBaseVO orderClaimBaseVO);

}
