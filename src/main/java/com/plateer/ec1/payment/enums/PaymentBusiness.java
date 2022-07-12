package com.plateer.ec1.payment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentBusiness {

    VACCT_APV("00"),
    VACCT_APV_CMP("00"),
    VACCT_CNL("00"),
    POINT_APV(""),
    POINT_CNL("");

    private final String sucessCode;

}
