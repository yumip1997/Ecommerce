package com.plateer.ec1.claim.strategy.validator.impl;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.strategy.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompleteValidator implements ClaimValidator {

    @Override
    public void isValid(List<ClaimView> claimViewList, ClaimDefine claimDefine) {
        isValidProductType(claimViewList, claimDefine.getPrdTypeStr());
        isValidOrdPrgsScd(claimViewList, claimDefine.getValidOrdPrgs());
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
