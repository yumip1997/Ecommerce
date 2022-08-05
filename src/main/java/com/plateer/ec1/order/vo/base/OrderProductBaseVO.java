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
    @Min(1)
    private int ordCnt;
    private long salePrc;
    private long prmPrc;
    private String goodsNoItemNo;

    public long getOrdPrc(){
        return this.prmPrc == 0 ? this.salePrc : this.prmPrc;
    }

    public long getOrdPrcWithOrdCnt(){
        return this.getOrdPrc() * this.ordCnt;
    }

    public String getGoodsNoItemNo(){
        if(this.goodsNoItemNo == null){
            this.goodsNoItemNo = this.goodsNo + this.itemNo;
        }
        return this.goodsNoItemNo;
    }
}
