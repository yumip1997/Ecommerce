package com.plateer.ec1.promotion.enums;

import com.plateer.ec1.common.excpetion.ExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum CupDwlTypeCode {

    GENERAL_CUP("10"),
    OFFLINE_CUP("40");

    private final String code;

    public static CupDwlTypeCode of(String code){
        return Arrays.stream(CupDwlTypeCode.values())
                .filter(prm0009Code -> prm0009Code.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }
}
