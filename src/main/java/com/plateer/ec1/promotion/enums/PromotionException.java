package com.plateer.ec1.promotion.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PromotionException {
    INVALID_PROMOTION_TYPE("프로모션 유형이 올바르지 않습니다!");

    private final String MSG;
}
