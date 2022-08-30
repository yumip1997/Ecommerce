package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OPT0006Code {

    DV_FEE("10"),
    RETURN_FEE("20"),
    EXCHANGE_FEE("30");

    public final String code;
}
