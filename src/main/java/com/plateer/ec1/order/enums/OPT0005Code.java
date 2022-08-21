package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0005Code {

    APPLY("10"),
    CANCEL("20");

    public final String code;

    public static String reverseCode(String code){
        if(OPT0005Code.APPLY.code.equals(code)){
            return OPT0005Code.CANCEL.code;
        }else{
            return OPT0005Code.APPLY.code;
        }
    }
}
