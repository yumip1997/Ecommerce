package com.plateer.ec1.claim.validation.verifier.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.verifier.AmountVerifier;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

//결제 인터페이스 안 태우는 것(주로 접수, 철회일 때)
@Component
public class CancelAmountVerifier implements AmountVerifier {

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(MCA, GRA, GRW, GEA, GEW);
    }

    @Override
    public void verifyAmount(Long reqCnlAmt, OrderClaimBaseVO orderClaimBaseVO) {

    }
}
