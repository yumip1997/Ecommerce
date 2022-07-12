package com.plateer.ec1.payment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.plateer.ec1.common.excpetion.ExceptionMessage;
import lombok.Getter;

import java.util.Arrays;

public enum PaymentBusiness {

    VACCT_APV("VA", "00"),
    VACCT_APV_CMP("VAC", "00"),
    VACCT_CNL("VC", "00"),
    POINT_APV("PA", ""),
    POINT_CNL("PC", "");

    private final String code;
    @Getter
    private final String sucessCode;

    PaymentBusiness(String code, String successCode){
        this.code = code;
        this.sucessCode = successCode;
    }

    @JsonValue
    public String getCode(){
        return code;
    }

    @JsonCreator
    public static PaymentBusiness of(String code){
        return Arrays.stream(PaymentBusiness.values())
                .filter(paymentBusiness -> paymentBusiness.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }

}
