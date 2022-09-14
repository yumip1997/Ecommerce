package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.ClaimException;
import com.plateer.ec1.claim.enums.ValidOrdPrgs;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import lombok.Builder;
import org.springframework.util.CollectionUtils;

@Builder
public class ClaimValidationVO {

    private String prdType;
    private ValidOrdPrgs validOrdPrgs;

    private ClaimView claimView;

    public void isNotEmptyClaimGoodsInfo(){
        if(CollectionUtils.isEmpty(claimView.getClaimGoodsInfoList())){
            throw new ValidationException(ClaimException.NOT_FOUND_CLAIM.MSG);
        }
    }

    public void isValidProductType() {
        for (ClaimGoodsInfo claimGoodsInfo : claimView.getClaimGoodsInfoList()) {
            if (this.prdType.equals(claimGoodsInfo.getGoodsSellTpCd())) continue;

            throw new ValidationException(ClaimException.INVALID_PRD_TPYE.MSG);
        }
    }

    public void isValidOrdPrgsScd(){
        for (ClaimGoodsInfo claimGoodsInfo : claimView.getClaimGoodsInfoList()) {
            if(validOrdPrgs.isContains(claimGoodsInfo.getOrdPrgsScd())) continue;

            throw new ValidationException(ClaimException.INVALID_ORDER_STATUS.MSG);
        }
    }
}
