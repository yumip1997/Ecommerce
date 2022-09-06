package com.plateer.ec1.claim.externals.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.externals.ExternalIFCallHelper;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
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
public class PaymentIFCallHelper implements ExternalIFCallHelper {

    private final PayService payService;

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(ClaimBusiness.GCC, ClaimBusiness.MCC);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void call(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO) {
        PaymentCancelReqVO paymentCancelReqVO = claimRequestVO.toPaymentCancelReqVO(creationVO.getClmNo());
        payService.cancel(paymentCancelReqVO);
    }
}
