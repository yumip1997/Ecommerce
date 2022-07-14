package com.plateer.ec1.payment.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentException {

    INVALID_PAYMENTTYPE("결제 유형이 올바르지 않습니다!"),
    FAIL_APPROVE("결제 승인에 실패하였습니다"),
    FAIL_VACCT_DEPOSIT("가상계좌 입금이 정상적으로 이루어지지 않았습니다!"),
    FAIL_CANCEL_PAY("결제 취소에 실패했습니다!");

    public final String MSG;
}
