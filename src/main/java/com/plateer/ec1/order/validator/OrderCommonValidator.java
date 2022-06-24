package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationVO;

import java.util.function.Predicate;

public class OrderCommonValidator {

   public static Predicate<OrderValidationVO> isSellingProudct = (OrderValidationDto -> true);

}
