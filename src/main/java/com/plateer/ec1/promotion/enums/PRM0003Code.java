package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.BiFunction;

@RequiredArgsConstructor
@Getter
public enum PRM0003Code {

    FIXED("10", (productPrice, fixedAmount) -> productPrice - fixedAmount),
    RATE("20", (productPrice, rate) -> productPrice  - productPrice * rate/100);

    private final String code;
    private final BiFunction<Long, Long, Long> discountFunction;

    public static BiFunction<Long, Long, Long> getDiscountFunction(String code){
        return Arrays.stream(PRM0003Code.values())
                .filter(PRM0003Code -> PRM0003Code.getCode().equals(code))
                .findFirst()
                .map(PRM0003Code::getDiscountFunction)
                .orElseThrow(() -> new IllegalArgumentException(PromotionException.INVALID_DC_TYPE.getMSG()));
    }

}
