package com.plateer.ec1.promotion.enums;

import com.plateer.ec1.common.excpetion.ExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.BinaryOperator;

@RequiredArgsConstructor
@Getter
public enum PRM0011Code {

    SAVE("10", Long::sum),
    USE("20", (pntBlc, amt) -> pntBlc - amt);

    private final String code;
    private final BinaryOperator<Long> binaryOperator;

    public static PRM0011Code of(String code){
        return Arrays.stream(PRM0011Code.values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }

    public static BinaryOperator<Long> getOperatorByCode(String code){
        return Arrays.stream(PRM0011Code.values())
                .filter(e -> e.code.equals(code))
                .findFirst()
                .map(PRM0011Code::getBinaryOperator)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }
}
