package com.plateer.ec1.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BankCode {

    IBK("03"),
    KB("04"),
    KEB("81");

    private final String code;
}
