package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0012Code {

    SC("S"),
    FD("FD"),
    FV("FV"),
    FP("FP");

    private final String code;
}
