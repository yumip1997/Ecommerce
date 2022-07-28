package com.plateer.ec1.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PRD0002Code {

    DIRECT_DELIVERY("10"),
    NOT_DELIVERY("20");

    private final String code;
}
