package com.plateer.ec1.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.plateer.ec1.common.excpetion.ExceptionMessage;

import java.util.Arrays;

@JsonFormat
public enum PaymentType {

    INICIS("inicis"),
    POINT("point");

    private final String code;

    PaymentType(String code){
        this.code = code;
    }

    @JsonValue
    public String getCode(){
        return code;
    }

    @JsonCreator
    public static PaymentType of(String code){
        return Arrays.stream(PaymentType.values())
                .filter(paymentType -> paymentType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }
}
