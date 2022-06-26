package com.plateer.ec1.promotion.apply.vo;

import com.plateer.ec1.common.model.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductCouponVO {

    private Product product;
    private List<PromotionVO> promotionVOList;

}
