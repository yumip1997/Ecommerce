package com.plateer.ec1.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0011Code {

    PAY_REQUEST("10"),
    PAY_COMPLETE("20"),
    CANCEL_COMPLETE("30");

    private final String code;
}
