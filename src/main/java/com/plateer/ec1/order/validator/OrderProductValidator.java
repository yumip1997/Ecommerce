package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderProductViewVO;

import java.util.function.Predicate;

public class OrderProductValidator {

    public static Predicate<OrderProductViewVO> isExistOrderPrd = vo ->
        vo.getOrderRequestVO()
                .getOrderProductVOList()
                .stream()
                .allMatch(orderProductVO -> vo.getProductInfoVOMap().containsKey(orderProductVO.getGoodsNoItemNo()));

    public static Predicate<OrderProductViewVO> validateMaxPurchaseCnt = orderValidationDto -> true;
    public static Predicate<OrderProductViewVO> validateMinPurchaseCnt = orderValidationDto -> true;

}
