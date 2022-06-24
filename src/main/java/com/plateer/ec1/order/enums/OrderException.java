package com.plateer.ec1.order.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderException {

    INVALID_SYSTEM_TYPE("시스템 타입이 올바르지 않습니다!"),
    INVALID_ORDER_TPYE("주문 유형이 올바르지 않습니다!"),
    NOT_FIND_VALIDATOR("Validator를 찾을 수 업습니다!"),
    INVALID_ORDER("주문이 불가합니다!");

    public final String msg;
}

