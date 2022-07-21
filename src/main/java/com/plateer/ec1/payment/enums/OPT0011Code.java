package com.plateer.ec1.payment.enums;

import com.plateer.ec1.common.excpetion.ExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum OPT0011Code {

    PAY_REQUEST("10"),
    PAY_COMPLETE("20"),
    CANCEL_COMPLETE("30");

    private final String code;

    public static OPT0011Code of(String code){
        return Arrays.stream(OPT0011Code.values())
                .filter(prgCode -> prgCode.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }
}
