package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PRM0004Code {

    PRODUCT_COUPON("10"),
    CART_COUPON("30");

    private final String code;

}
