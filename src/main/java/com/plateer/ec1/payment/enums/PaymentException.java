package com.plateer.ec1.payment.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentException {

    INVALID_PAYMENTTYPE("결제 유형이 올바르지 않습니다!");

    public final String MSG;
}
