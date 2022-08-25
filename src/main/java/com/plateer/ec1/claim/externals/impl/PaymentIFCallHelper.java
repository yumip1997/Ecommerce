package com.plateer.ec1.claim.externals.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.externals.ExternalIFCallHelper;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.payment.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentIFCallHelper implements ExternalIFCallHelper {

    private final PayService payService;

    @Override
    public void call(ClaimRequestVO claimRequestVO) {
        payService.cancel(claimRequestVO.toPaymentCancelReqVO());
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(ClaimBusiness.GCC, ClaimBusiness.MCC);
    }
}
