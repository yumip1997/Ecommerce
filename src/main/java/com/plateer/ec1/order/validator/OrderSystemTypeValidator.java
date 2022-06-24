package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationDto;

import java.util.function.Predicate;

public class OrderSystemTypeValidator {

    public static Predicate<OrderValidationDto> isFOOrderAbleProduct = (orderRequest -> true);
    public static Predicate<OrderValidationDto> isBOOrderAbleProduct = (orderRequest -> true);

}
