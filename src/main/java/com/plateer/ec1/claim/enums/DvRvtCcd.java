package com.plateer.ec1.claim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DvRvtCcd {

    DELIVERY("10"),
    RETRIEVE("20");

    private final String code;

}
