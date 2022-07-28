package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderProductViewVO;

import java.util.function.Predicate;

public class OrderSystemTypeValidator {

    public static Predicate<OrderProductViewVO> isFOOrderAbleProduct = (orderRequest -> true);
    public static Predicate<OrderProductViewVO> isBOOrderAbleProduct = (orderRequest -> true);

}
