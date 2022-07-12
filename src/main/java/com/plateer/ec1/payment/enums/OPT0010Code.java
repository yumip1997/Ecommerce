package com.plateer.ec1.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0010Code {

    PAY("10"),
    CANCEL("20");

    private final String code;

}
