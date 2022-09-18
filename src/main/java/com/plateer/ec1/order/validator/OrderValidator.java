package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@RequiredArgsConstructor
public enum OrderValidator {

    FO_GENERAL(SystemType.FO.getCode(), OPT0001Code.GENERAL.code,
            OrderProductValidator.validateExistPrd
                    .andThen(OrderProductValidator.validateIsSelling)
                    .andThen(OrderProductValidator.validateGeneralPrd)
                    .andThen(OrderProductValidator.validateGeneralDelivery),
            OrderDeliveryValidator.validateGeneralParam
    ),
    BO_GENERAL(SystemType.BO.getCode(), OPT0001Code.GENERAL.code,
            OrderProductValidator.validateExistPrd
                    .andThen(OrderProductValidator.validateIsSelling)
                    .andThen(OrderProductValidator.validateGeneralPrd)
                    .andThen(OrderProductValidator.validateGeneralDelivery),
            OrderDeliveryValidator.validateGeneralParam
    ),
    FO_ECOUPON(SystemType.FO.getCode(), OPT0001Code.ECOUPON.code,
            OrderProductValidator.validateExistPrd
                    .andThen(OrderProductValidator.validateIsSelling)
                    .andThen(OrderProductValidator.validateECouponPrd)
                    .andThen(OrderProductValidator.validateECouponDelivery),
            OrderDeliveryValidator.validateEcouponParam
                    .andThen(OrderDeliveryValidator.validateEcouponCnt)
    ),
    BO_ECOUPON(SystemType.BO.getCode(), OPT0001Code.ECOUPON.code,
            OrderProductValidator.validateExistPrd
                    .andThen(OrderProductValidator.validateIsSelling)
                    .andThen(OrderProductValidator.validateECouponPrd)
                    .andThen(OrderProductValidator.validateECouponDelivery),
            OrderDeliveryValidator.validateEcouponParam
                    .andThen(OrderDeliveryValidator.validateEcouponCnt)
    );

    private final String systemTypeCode;
    private final String orderTypeCode;
    private final Consumer<ProductInfoVO> productInfoVOPredicate;
    private final Consumer<OrderRequestVO> deliveryPredicate;

    public void isValid(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        isValidOrderBasic(orderRequestVO);
        isValidDelivery(orderRequestVO);
        isValidOrdPrd(orderProductViewList);
    }

    private void isValidOrderBasic(OrderRequestVO orderRequestVO){
        if(orderRequestVO.isContainsVacctPayment()){
            OrderPaymentValidator.validateVacctParam.accept(orderRequestVO.getOrderBasicVO());
        }
    }

    private void isValidDelivery(OrderRequestVO orderRequestVO){
        deliveryPredicate.accept(orderRequestVO);
    }

    private void isValidOrdPrd(List<OrderProductView> orderProductViewList){
        for (OrderProductView orderProductView : orderProductViewList) {
            productInfoVOPredicate.accept(orderProductView.getProductInfoVO());
        }
    }

    public static OrderValidator findOrderValidator(String systemType, String orderType){
        return Arrays.stream(OrderValidator.values())
                .filter(e -> e.systemTypeCode.equals(systemType) && e.orderTypeCode.equals(orderType))
                .findFirst()
                .orElseThrow(() -> new ValidationException(OrderException.NOT_FIND_VALIDATOR.msg));
    }

}
