package com.plateer.ec1.product.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PRD0003Code {

    BF_APV("10"),
    SELLING("20"),
    TM_STP("30"),
    PM_STP("40");

    private final String code;
}
