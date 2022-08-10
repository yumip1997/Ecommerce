package com.plateer.ec1.claim.strategy.validator.impl;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.ValidCode;
import com.plateer.ec1.claim.strategy.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimRequestVO;

public class AcceptWithdrawalValidator implements ClaimValidator {

    @Override
    public void isValid(ClaimRequestVO claimRequestVO, ValidCode validCode){
        isValidOrdPrgsScd(claimRequestVO, validCode.getValidOrdPrgsCode());
        isValidProductType(claimRequestVO, validCode.getValidPrdCode());
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
