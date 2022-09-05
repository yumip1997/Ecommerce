package com.plateer.ec1.claim.externals;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentIFCallHelper implements MultiValueCustomFactory<ClaimBusiness> {

    private final PayService payService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void call(PaymentCancelReqVO paymentCancelReqVO) {
        payService.cancel(paymentCancelReqVO);
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(ClaimBusiness.GCC, ClaimBusiness.MCC);
    }
}
