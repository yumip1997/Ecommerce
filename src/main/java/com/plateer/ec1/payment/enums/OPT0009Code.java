package com.plateer.ec1.payment.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.plateer.ec1.common.excpetion.ExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
@JsonFormat
public enum OPT0009Code {

    VIRTUAL_ACCOUNT("10"),
    POINT("20");

    private final String code;

    @JsonValue
    public String getCode() {
        return code;
    }

    public static OPT0009Code of(String code){
        return Arrays.stream(OPT0009Code.values())
                .filter(methodCode -> methodCode.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }

}
