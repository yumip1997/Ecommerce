package com.plateer.ec1.order.enums;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum OrderType{

    ECOUPON("EC"),
    GENERAL("GL");

    private final String code;

    public static OrderType findOrderType(String orderTypeCode){
        return Arrays.stream(OrderType.values())
                .filter(orderType -> orderType.getCode().equals(orderTypeCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException(OrderException.INVALID_ORDER_TPYE.msg));
    }
}
