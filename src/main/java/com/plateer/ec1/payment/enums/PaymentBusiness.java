package com.plateer.ec1.payment.enums;

import com.plateer.ec1.common.excpetion.ExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentBusiness {

    VACCT_APV("VA", "00", PaymentType.INICIS, OPT0009Code.VIRTUAL_ACCOUNT, OPT0011Code.PAY_REQUEST),
    VACCT_DPST_CMT("VAC", "0200",  PaymentType.INICIS, OPT0009Code.VIRTUAL_ACCOUNT, OPT0011Code.PAY_COMPLETE),
    VACCT_CNL("VC", "00",  PaymentType.INICIS, OPT0009Code.VIRTUAL_ACCOUNT, OPT0011Code.CANCEL_COMPLETE),
    POINT_APV("PA", "",  PaymentType.POINT, OPT0009Code.POINT, OPT0011Code.PAY_COMPLETE),
    POINT_CNL("PC", "",  PaymentType.POINT, OPT0009Code.POINT, OPT0011Code.CANCEL_COMPLETE);

    private final String code;
    private final String sucessCode;
    private final PaymentType paymentType;
    private final OPT0009Code methodCode;
    private final OPT0011Code payPrgsCode;

    public static PaymentBusiness of(PaymentType paymentType, OPT0009Code methodCode){
        return Arrays.stream(PaymentBusiness.values())
                .filter(business -> business.getPaymentType().equals(paymentType) && business.getMethodCode().equals(methodCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.getMsg()));
    }

}
