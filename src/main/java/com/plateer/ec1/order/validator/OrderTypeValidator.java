package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationDto;

import java.util.function.Predicate;

public class OrderTypeValidator {

    public static Predicate<OrderValidationDto> isGeneralOrderAbleProduct = (orderRequest -> true);
    public static Predicate<OrderValidationDto> isEcouponOrderAbleProduct = (orderRequest -> true);

}
