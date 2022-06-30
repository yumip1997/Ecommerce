package com.plateer.ec1.promotion.apply.vo.response;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PriceDiscountResponseVO extends ResponseBaseVO {

    private List<ProductInfoVO> productInfoVOList;

    @Builder
    public PriceDiscountResponseVO(String mbrNo, List<ProductInfoVO> productInfoVOList) {
        super(mbrNo);
        this.productInfoVOList = productInfoVOList;
    }
}
