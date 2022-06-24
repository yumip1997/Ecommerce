package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum PromotionType {

    //가격조정
    PRICE_DISCOUNT("PD"),
    //상품쿠폰
    PROUDCT_COUPON("PC"),
    //장바구니쿠폰
    CART_COUPON("CC");

    private final String code;

    public static PromotionType findPromotionType(String code){
        return Arrays.stream(PromotionType.values())
                .filter(promotionType -> promotionType.getCode().equals(code))
                .findFirst().orElse(null);
    }
}
