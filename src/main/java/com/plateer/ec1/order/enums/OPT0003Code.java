package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0003Code {

    ORDER("O"),
    CANCEL("C"),
    RETURN("R"),
    RETURN_WITHDRAWAL("RC"),
    EXCHANGE_ACCEPT("X"),
    EXCHANGE_WITHDRAWAL("XC");

    public final String code;

}
