package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationDto;

import java.util.function.Predicate;

public class OrderProductValidator {

    public static Predicate<OrderValidationDto> validateMaxPurchaseCnt = (orderValidationDto) -> true;
    public static Predicate<OrderValidationDto> validateMinPurchaseCnt = (orderValidationDto) -> true;

}
