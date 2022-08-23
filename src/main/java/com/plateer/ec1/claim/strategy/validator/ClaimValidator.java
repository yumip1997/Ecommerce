package com.plateer.ec1.claim.strategy.validator;

import com.plateer.ec1.claim.enums.ClaimException;
import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.define.ValidOrdPrgs;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.common.factory.CustomFactory;

import java.util.List;

public interface ClaimValidator extends CustomFactory<ClaimStatusType> {

    void isValid(List<ClaimView> claimViewList, ClaimDefine claimDefine);

    default void isValidOrdPrgsScd(List<ClaimView> claimViewList, ValidOrdPrgs validOrdPrgs){
        for (ClaimView claimView : claimViewList) {
            if(validOrdPrgs.isContains(claimView.getOrdPrgsScd())) continue;

            throw new ValidationException(ClaimException.INVALID_ORDER_STATUS.MSG);
        }
    }

    default void isValidProductType(List<ClaimView> claimViewList, String prdType){
        for (ClaimView claimView : claimViewList) {
            if(prdType.equals(claimView.getGoodsSellTpCd())) continue;

            throw new ValidationException(ClaimException.INVALID_PRD_TPYE.MSG);
        }
    }

    void verifyAmount(ClaimRequestVO claimRequestVO);

}

