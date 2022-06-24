package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationVO;

import java.util.function.Predicate;

public class OrderProductValidator {

    public static Predicate<OrderValidationVO> validateMaxPurchaseCnt = (orderValidationDto) -> true;
    public static Predicate<OrderValidationVO> validateMinPurchaseCnt = (orderValidationDto) -> true;

}
