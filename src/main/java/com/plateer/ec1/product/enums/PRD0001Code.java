package com.plateer.ec1.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PRD0001Code {

    GENERAL("10"),
    ECOUPON("20");

    private final String code;
}
