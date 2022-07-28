package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderProductViewVO;
import com.plateer.ec1.product.vo.ProductInfoVO;

import java.util.function.Predicate;

public class OrderCommonValidator {

   public static Predicate<OrderProductViewVO> isSellingProduct = vo -> vo.getProductInfoVOList().stream().allMatch(ProductInfoVO::isSelling);

}
