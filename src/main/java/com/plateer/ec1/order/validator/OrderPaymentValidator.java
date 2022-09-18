package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.vo.OrderBasicVO;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class OrderPaymentValidator {

    public static Consumer<OrderBasicVO> validateVacctParam =  (vo) -> {
        boolean isValid = StringUtils.isNotEmpty(vo.getRfndAcctNo())
                && StringUtils.isNotEmpty(vo.getRfndBnkCk())
                && StringUtils.isNotEmpty(vo.getRfndAcctOwnNm());

        if(isValid) return;

        throw new ValidationException(OrderException.INVALID_ORDER_TPYE.msg);
    };
}
