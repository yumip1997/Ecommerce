package com.plateer.ec1.claim.externals.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.externals.ExternalIFCallHelper;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(1)
public class PaymentApproveIFCallHelper implements ExternalIFCallHelper {

    @Override
    public void call(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO) {
        //반품접수 - 환불비 < 반품배송비
        //교환접수 - 교환배송비 있을 경우
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(ClaimBusiness.GRA, ClaimBusiness.GEA);
    }
}
