package com.plateer.ec1.promotion.apply.vo.response;

import com.plateer.ec1.common.model.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PriceDiscountResponseVO extends ResponseBaseVO {

    private List<Product> productList;

    @Builder
    public PriceDiscountResponseVO(String mbrNo, List<Product> productList) {
        super(mbrNo);
        this.productList = productList;
    }
}
