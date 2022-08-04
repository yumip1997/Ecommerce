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
    @NotEmpty
    private String goodsNm;
    @NotEmpty
    private String itemNm;
    @Min(1)
    private int ordCnt;

    private long ordAmt;

    private long salePrc;

    private long prmPrc;

    private String goodsNoItemNo;

    protected long getOrdPrcWithOrdCnt(){
        long ordPrc = this.prmPrc == 0 ? this.salePrc : this.prmPrc;
        return ordPrc * this.ordCnt;
    }

    public String getGoodsNoItemNo(){
        if(this.goodsNoItemNo == null){
            this.goodsNoItemNo = this.goodsNo + this.itemNo;
        }
        return this.goodsNoItemNo;
    }
}
