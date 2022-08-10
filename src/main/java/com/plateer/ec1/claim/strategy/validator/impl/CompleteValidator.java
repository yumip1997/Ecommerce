package com.plateer.ec1.claim.strategy.validator.impl;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.ValidCode;
import com.plateer.ec1.claim.strategy.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimRequestVO;

public class CompleteValidator implements ClaimValidator {

    @Override
    public void isValid(ClaimRequestVO claimRequestVO, ValidCode validCode){
        isValidOrdPrgsScd(claimRequestVO, validCode.getValidOrdPrgsCode());
        isValidProductType(claimRequestVO, validCode.getValidPrdCode());
    }

    @Override
    public void verifyAmount(ClaimRequestVO claimRequestVO){
        //주문결제테이블의 결제금액 - 취소금액 = 환불가능금액 인지 판단
    }

    @Override
    public ClaimStatusType getType() {
        return ClaimStatusType.COMPLETE;
    }
}
