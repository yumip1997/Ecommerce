package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.ClaimException;
import com.plateer.ec1.claim.enums.ValidOrdPrgs;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import lombok.Builder;

import java.util.List;

@Builder
public class ClaimValidationVO {

    private String prdType;
    private ValidOrdPrgs validOrdPrgs;

    private List<ClaimView> claimViewList;

    public void isValidProductType() {
        for (ClaimView claimView : this.claimViewList) {
            if (this.prdType.equals(claimView.getGoodsSellTpCd())) continue;

            throw new ValidationException(ClaimException.INVALID_PRD_TPYE.MSG);
        }
    }

    public void isValidOrdPrgsScd(){
        for (ClaimView claimView : this.claimViewList) {
            if(validOrdPrgs.isContains(claimView.getOrdPrgsScd())) continue;

            throw new ValidationException(ClaimException.INVALID_ORDER_STATUS.MSG);
        }
    }
}
