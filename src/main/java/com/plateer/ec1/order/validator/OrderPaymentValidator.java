package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderBasicVO;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

public class OrderPaymentValidator {

    public static Predicate<OrderBasicVO> vacctPredicate = vo -> StringUtils.isNotEmpty(vo.getRfndAcctNo())
            && StringUtils.isNotEmpty(vo.getRfndBnkCk())
            && StringUtils.isNotEmpty(vo.getRfndAcctOwnNm());
}
