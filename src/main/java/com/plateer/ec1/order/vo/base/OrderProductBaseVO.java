package com.plateer.ec1.order.vo.base;

import com.plateer.ec1.product.vo.ProductInfoVO;
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

    public ProductInfoVO toProductInfoVO(){
        return ProductInfoVO.builder()
                .goodsNo(this.getGoodsNo())
                .itemNo(this.getItemNo())
                .build();
    }
}
