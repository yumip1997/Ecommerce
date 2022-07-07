package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BinaryOperator;

@RequiredArgsConstructor
@Getter
public enum PRM0003Code {

    FIXED("10", (productPrice, fixedAmount) -> fixedAmount),
    RATE("20", (productPrice, rate) -> productPrice * rate/100);

    private final String code;
    private final BinaryOperator<Long> discountFunction;

    public static BinaryOperator<Long> getBnfValFunction(String code){
        if(FIXED.getCode().equals(code)){
            return FIXED.getDiscountFunction();
        }

        if(RATE.getCode().equals(code)){
            return RATE.getDiscountFunction();
        }

        throw new IllegalArgumentException(PromotionException.INVALID_DC_TYPE.getMSG());
    }
}
