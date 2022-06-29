package com.plateer.ec1.promotion.apply.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartCouponResponseVO extends ResponseBaseVO {
    @Builder
    public CartCouponResponseVO(String mbrNo) {
        super(mbrNo);
    }
}
