package com.plateer.ec1.claim.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ClaimException {

    NOT_FOUND_CLAIM("클레임 대상 주문 건을 찾을 수 없습니다!"),
    INVALID_CLAIM_TYPE ("클레임 타입이 올바르지 않습니다!"),
    INVALID_ORDER_STATUS("클레임 가능한 주문 상태가 아닙니다!"),
    INVALID_PRD_TPYE("상품 유형이 올바르지 않습니다!"),
    INVALID_AMOUNT("금액이 일치하지 않습니다");

    public final String MSG;
}
