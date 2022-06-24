package com.plateer.ec1.promotion.vo;

import com.plateer.ec1.common.model.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplicablePromotionVO {

    private Product product;
    private List<PromotionVO> promotionVOList;

}
