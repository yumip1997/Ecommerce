package com.plateer.ec1.delivery.enums;

import lombok.RequiredArgsConstructor;

/*
배송비 정책 구분코드
 */
@RequiredArgsConstructor
public enum DVP0002Code {

    FREE("10"),
    PAY("20"),
    PAY_ON_DELIVERY("30");

    public final String code;

}
