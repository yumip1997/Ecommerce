package com.plateer.ec1.claim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrdClmTpCd {

    CANCEL("C"),
    RETURN("R"),
    RETURN_WITHDRAWAL("RC"),
    EXCHANGE_ACCEPT("X"),
    EXCHANGE_WITHDRAWAL("XC");

    private final String code;

}
