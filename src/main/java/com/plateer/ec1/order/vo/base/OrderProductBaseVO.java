package com.plateer.ec1.order.vo.base;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderProductBaseVO {

    @NotNull
    private String goodsNo;
    @NotNull
    private String itemNo;

    private String goodsNm;
    private String itemNm;

    public ProductInfoVO toProductInfoVO(){
        return ProductInfoVO.builder()
                .goodsNo(this.getGoodsNo())
                .itemNo(this.getItemNo())
                .build();
    }

    public String getGoodsNoItemNo(){
        return this.getGoodsNo() + this.getItemNo();
    }
}
