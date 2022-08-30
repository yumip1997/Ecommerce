package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OPT0008Code {

    CUSTOMER("10"),
    COMPANY("20");

    public final String code;
}
