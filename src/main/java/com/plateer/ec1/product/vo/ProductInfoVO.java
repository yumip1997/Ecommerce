package com.plateer.ec1.product.vo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoVO {

    private String goodsItemNo;
    private String prmCpnIssNo;
    private String goodsNo;
    private String goodsNm;
    private String itemNo;
    private String itemNm;
    private String goodsTpCd;
    private Long salePrc;
    private Long prmPrc;
    private Long orrAt;
    private Long orrCnt;

}
