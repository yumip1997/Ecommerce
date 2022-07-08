package com.plateer.ec1.product.vo;

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
    private Long orrAt;
    private Long orrCnt;
    private Long appliedCpnIssNo;

    public Long getTotalPriceWithCnt(){
        return this.getOrrAt() * this.getOrrCnt();
    }

}
