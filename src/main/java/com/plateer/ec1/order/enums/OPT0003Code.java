package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0003Code {

    ORDER("O"),
    CANCEL("C"),
    RETURN_ACCEPT("R"),
    RETURN_WITHDRAWAL("RC"),
    EXCHANGE_ACCEPT("X");

    public final String code;

}
