package com.plateer.ec1.claim.strategy.validator.impl;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.strategy.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AcceptWithdrawalValidator implements ClaimValidator {

    @Override
    public void isValid(List<ClaimView> claimViewList, ClaimDefine claimDefine) {
        isValidProductType(claimViewList, claimDefine.getPrdTypeStr());
        isValidOrdPrgsScd(claimViewList, claimDefine.getValidOrdPrgs());
    }

    @Override
    public void verifyAmount(ClaimRequestVO claimRequestVO){
        //UI 상의 취소 금액 = 주문금액 - 혜택금액 -배송비 = 취소금액
    }

    @Override
    public ClaimStatusType getType() {
        return ClaimStatusType.ACCEPT_WITHDRAWAL;
    }
}
