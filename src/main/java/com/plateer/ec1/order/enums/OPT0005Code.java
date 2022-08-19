package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0005Code {

    APPLY("10"),
    CANCEL("20");

    public final String code;
}
