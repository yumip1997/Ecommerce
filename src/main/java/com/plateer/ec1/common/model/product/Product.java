package com.plateer.ec1.common.model.product;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String goodsNo;
    private String goodsNm;
    private String itemNo;
    private String itemNm;
    private String goodsTpCd;
    private Long salePrc;
    private Long prmPrc;
    private Long orrAt;

    public Product(String goodsNo, String itemNo){
        this.goodsNo = goodsNo;
        this.itemNo = itemNo;
    }


}
