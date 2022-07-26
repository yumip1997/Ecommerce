package com.plateer.ec1.order.enums;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum SystemType {
    FO("FO"),
    BO("BO");

    private final String code;

    public static SystemType findSystemType(String systemTypeCode){
        return Arrays.stream(SystemType.values())
                .filter(orderType -> orderType.getCode().equals(systemTypeCode))
                .findFirst()
                .orElseThrow(() -> new BusinessException(OrderException.INVALID_SYSTEM_TYPE.msg));
    }
}
