package com.plateer.ec1.promotion.vo.response;

import com.plateer.ec1.promotion.vo.ProductCouponVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCouponResponseVO extends ResponseBaseVO {

    private List<ProductCouponVO> productCouponVOList;

}
