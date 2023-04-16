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
public class PaymentCancelIFCallHelper implements ExternalIFCallHelper {

    @Override
    public void call(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO) {
        // 주문취소 -> 환불
        // 교환접수 -> 교환배송비 발생 시 환불
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(ClaimBusiness.GCC, ClaimBusiness.MCC, ClaimBusiness.GEW);
    }
}
