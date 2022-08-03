package com.plateer.ec1.delivery.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DVP0001Code {

    DELIVERY("10"),
    NON_DELIVERY("20");

    private final String code;

}
