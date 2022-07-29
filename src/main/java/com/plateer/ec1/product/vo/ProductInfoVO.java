package com.plateer.ec1.product.vo;

import com.plateer.ec1.product.enums.PRD0001Code;
import com.plateer.ec1.product.enums.PRD0002Code;
import com.plateer.ec1.product.enums.PRD0003Code;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoVO {

    private String goodsNo;
    private String goodsNm;
    private String itemNo;
    private String itemNm;
    private String goodsTpCd;
    private Long salePrc;
    private Long prmPrc;
    private String prgsStatCd;
    private String goodsDlvTpCd;
    private Long orrAt;
    private Long orrCnt;
    private Long appliedCpnIssNo;

    public String getGoodNoItemNo(){
        return this.getGoodsNo() + this.getItemNo();
    }

    public boolean isSelling(){
        return PRD0003Code.SELLING.getCode().equals(this.getPrgsStatCd());
    }

    public boolean isGeneralProduct(){
        return PRD0001Code.GENERAL.getCode().equals(this.getGoodsTpCd());
    }

    public boolean isGeneralDelivery(){
        return PRD0002Code.DIRECT_DELIVERY.getCode().equals(this.getGoodsDlvTpCd());
    }

    public boolean isECouponProduct(){
        return PRD0001Code.ECOUPON.getCode().equals(this.getGoodsTpCd());
    }

    public boolean isECouponDelivery() {return PRD0001Code.ECOUPON.getCode().equals(this.getGoodsDlvTpCd());}

}
