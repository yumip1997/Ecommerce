package com.plateer.ec1.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum SystemType {
    FO("FO"),
    BO("BO");

    private final String code;

    public static SystemType findSystemType(String systemTypeCode) throws Exception {
        return Arrays.stream(SystemType.values())
                .filter(orderType -> orderType.getCode().equals(systemTypeCode))
                .findFirst()
                .orElseThrow(() -> new Exception(OrderException.INVALID_SYSTEM_TYPE.msg));
    }
}
