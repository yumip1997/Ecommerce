package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum OrderType{

    ECOUPON("EC"),
    GENERAL("GL");

    private final String code;

    public static OrderType findOrderType(String orderTypeCode) throws Exception {
        return Arrays.stream(OrderType.values())
                .filter(orderType -> orderType.getCode().equals(orderTypeCode))
                .findFirst()
                .orElseThrow(() -> new Exception(OrderException.INVALID_ORDER_TPYE.msg));
    }
}
