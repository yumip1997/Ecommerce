package com.plateer.ec1.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommonConstants {

    Y("Y"),
    N("N"),
    OK("OK"),
    FAIL("FAIL");

    private final String code;

}
