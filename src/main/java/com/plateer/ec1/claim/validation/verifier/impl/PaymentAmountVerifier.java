package com.plateer.ec1.claim.validation.verifier.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.verifier.AmountVerifier;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.GCC;
import static com.plateer.ec1.claim.enums.ClaimBusiness.MCC;

//결제 인터페이스 태우는 것 (완료일 때..)
@Component
public class PaymentAmountVerifier implements AmountVerifier {

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCC);
    }


    @Override
    public void verifyAmount(Long reqCnlAmt, OrderClaimBaseVO orderClaimBaseVO) {

    }
}