package com.plateer.ec1.claim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GoodsSellTpCd {

    GENERAL("G"),
    ECOUPON("EC");

    private final String code;

}
