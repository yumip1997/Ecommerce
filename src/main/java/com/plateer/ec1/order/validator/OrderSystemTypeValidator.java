package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderValidationVO;

import java.util.function.Predicate;

public class OrderSystemTypeValidator {

    public static Predicate<OrderValidationVO> isFOOrderAbleProduct = (orderRequest -> true);
    public static Predicate<OrderValidationVO> isBOOrderAbleProduct = (orderRequest -> true);

}
