package com.plateer.ec1.order.vo.base;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @Min(0)
    private int ordCnt;

    private long ordAmt;

    private long salePrc;

    private long prmPrc;

    protected long getOrdPrcWithOrdCnt(){
        long ordPrc = this.prmPrc == 0 ? this.salePrc : this.prmPrc;
        return ordPrc * this.ordCnt;
    }

    public String getGoodsNoItemNo(){
        return this.getGoodsNo() + this.getItemNo();
    }
}
