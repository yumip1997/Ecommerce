package com.plateer.ec1.order.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderException {

    INVALID_SYSTEM_TYPE("시스템 타입이 올바르지 않습니다!"),
    INVALID_ORDER_TPYE("주문 유형이 올바르지 않습니다!"),
    NOT_FIND_VALIDATOR("Validator를 찾을 수 업습니다!"),
    INVALID_ORDER("주문이 불가합니다!"),
    DATA_CREATION_ERROR("데이터 생성 시 오류가 발생했습니다!"),
    INVALID_AMT("금액 검증에 실패하였습니다!"),
    NOT_EXIST_PRODUCT("상품이 존재하지 않습니다."),
    INVALID_SELLING("현재 판매중인 상품이 아닙니다."),
    NOT_GENERAL_PRODUCT_TYPE("일반상품 주문인데 상품 유형이 일반상품이 아닙니다."),
    NOT_ECOUPON_PRODUCT_TYPE("모바일쿠폰 주문인데 상품 유형이 모바일쿠폼이 아닙니다."),
    NOT_GENERAL_DELIVERY("일반상품 배송 유형이 아닙니다."),
    NOT_ECOUPON_DELIVERY("모바일쿠폰 배송 유형이 아닙니다.");


    public final String msg;
}

