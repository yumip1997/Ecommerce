package com.plateer.ec1.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0009Code {

    VIRTUAL_ACCOUNT("10"),
    POINT("20");

    private final String code;
}
