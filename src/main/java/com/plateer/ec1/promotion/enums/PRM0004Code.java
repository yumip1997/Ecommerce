package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum PRM0004Code {

    //가격조정
    PRICE_DISCOUNT("10"),
    //상품쿠폰
    PROUDCT_COUPON("20"),
    //장바구니쿠폰
    CART_COUPON("30");

    private final String code;

    public static PRM0004Code findPromotionType(String code){
        return Arrays.stream(PRM0004Code.values())
                .filter(promotionType -> promotionType.getCode().equals(code))
                .findFirst().orElse(null);
    }
}
