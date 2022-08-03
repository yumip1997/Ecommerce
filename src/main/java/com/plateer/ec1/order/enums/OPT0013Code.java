package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0013Code {

    DELIVERY("10"),
    RETRIEVE("20");

    private final String code;

}
