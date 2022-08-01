package com.plateer.ec1.order.enums;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum OPT0001Code {

    GENERAL("10"),
    ECOUPON("20");

    private final String code;

    public static OPT0001Code findOrderType(String orderTypeCode){
        return Arrays.stream(OPT0001Code.values())
                .filter(OPT0001Code -> OPT0001Code.getCode().equals(orderTypeCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException(OrderException.INVALID_ORDER_TPYE.msg));
    }
}
