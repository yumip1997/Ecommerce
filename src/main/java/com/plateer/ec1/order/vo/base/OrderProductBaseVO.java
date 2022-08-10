package com.plateer.ec1.order.vo.base;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class OrderProductBaseVO {

    @NotEmpty
    private String goodsNo;
    @NotEmpty
    private String itemNo;
    private String goodsNm;
    private String itemNm;
    private String goodsNoItemNo;
    private String goodsTpCd;

    public String getGoodsNoItemNo(){
        if(this.goodsNoItemNo == null){
            this.goodsNoItemNo = this.goodsNo + this.itemNo;
        }
        return this.goodsNoItemNo;
    }
}
