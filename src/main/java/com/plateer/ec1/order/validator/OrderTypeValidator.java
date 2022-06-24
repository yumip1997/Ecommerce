package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationVO;

import java.util.function.Predicate;

public class OrderTypeValidator {

    public static Predicate<OrderValidationVO> isGeneralOrderAbleProduct = (orderRequest -> true);
    public static Predicate<OrderValidationVO> isEcouponOrderAbleProduct = (orderRequest -> true);

}
