package com.plateer.ec1.order.vo.base;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
