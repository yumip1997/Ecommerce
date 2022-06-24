package com.plateer.ec1.promotion.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.function.BiFunction;

@Getter
public enum DcCode {

    FIXED("FD", (productPrice, fixedAmount) -> productPrice - fixedAmount),
    RATE("RD", (productPrice, rate) -> productPrice  - productPrice * rate/100);

    private String code;
    private BiFunction<Long, Long, Long> discountFunction;

    DcCode(String code, BiFunction<Long, Long, Long> discountFunction){
        this.code = code;
        this.discountFunction = discountFunction;
    }

    //TODO 해당 CODE 없으면 THROW 로직으로 변경하기
    public static BiFunction<Long, Long, Long> getDiscountFunction(String code){
        return Arrays.asList(DcCode.values()).stream()
                .filter(dcCode -> dcCode.getCode().equals(code))
                .findFirst()
                .map(DcCode::getDiscountFunction)
                .orElse(null);
    }

}
