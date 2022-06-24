package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationDto;

import java.util.function.Predicate;

public class OrderCommonValidator {

   public static Predicate<OrderValidationDto> isSellingProudct = (OrderValidationDto -> true);

}
