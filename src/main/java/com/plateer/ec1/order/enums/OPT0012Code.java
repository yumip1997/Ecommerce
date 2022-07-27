package com.plateer.ec1.order.enums;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.excpetion.custom.DataCreationException;
import com.plateer.ec1.common.excpetion.custom.PaymentException;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OPT0012Code {

    SC("S", null),
    FD("FD", DataCreationException.class),
    FV("FV", ValidationException.class),
    FP("FP", PaymentException.class);

    private final String code;
    private final Class<? extends BusinessException> exceptionType;


    public static String getCodeByException(Exception exception){
        if(exception == null){
            return OPT0012Code.SC.getCode();
        }

        for (OPT0012Code value : OPT0012Code.values()) {
            if(value.getExceptionType() == null) continue;

            if(!isInstanceOf(value, exception)) continue;

            return value.getCode();
        }

        return "";
    }

    private static boolean isInstanceOf(OPT0012Code code, Exception instance){
        return code.getExceptionType().isInstance(instance);
    }


}
