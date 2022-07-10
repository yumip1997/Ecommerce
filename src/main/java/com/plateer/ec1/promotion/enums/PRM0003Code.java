package com.plateer.ec1.promotion.enums;

import com.plateer.ec1.common.excpetion.ExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.BinaryOperator;

@RequiredArgsConstructor
@Getter
public enum PRM0003Code {

    FIXED("10", (productPrice, fixedAmount) -> fixedAmount),
    RATE("20", (productPrice, rate) -> productPrice * rate/100);

    private final String code;
    private final BinaryOperator<Long> discountFunction;

    public static PRM0003Code of(String code){
        return Arrays.stream(PRM0003Code.values())
                .filter(prm0003Code -> prm0003Code.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }
}
