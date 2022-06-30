package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PRM0001Code {

    PRICE_DISCOUNT("10"),
    COUPON("20");

    private final String code;

}
