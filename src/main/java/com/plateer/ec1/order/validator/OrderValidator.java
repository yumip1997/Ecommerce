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
            OrderDeliveryValidator.generalParamPredicate
    ),
    BO_GENERAL(SystemType.BO.getCode(), OPT0001Code.GENERAL.code,
            OrderProductValidator.validateExistPrd
                    .andThen(OrderProductValidator.validateIsSelling)
                    .andThen(OrderProductValidator.validateGeneralPrd)
                    .andThen(OrderProductValidator.validateGeneralDelivery),
            OrderDeliveryValidator.generalParamPredicate
    ),
    FO_ECOUPON(SystemType.FO.getCode(), OPT0001Code.ECOUPON.code,
            OrderProductValidator.validateExistPrd
                    .andThen(OrderProductValidator.validateIsSelling)
                    .andThen(OrderProductValidator.validateECouponPrd)
                    .andThen(OrderProductValidator.validateECouponDelivery),
            OrderDeliveryValidator.generalParamPredicate
                    .and(OrderDeliveryValidator.eCouponParamPredicate)
                    .and(OrderDeliveryValidator.eCoupondeliveryCntPredicate)
    ),
    BO_ECOUPON(SystemType.BO.getCode(), OPT0001Code.ECOUPON.code,
            OrderProductValidator.validateExistPrd
                    .andThen(OrderProductValidator.validateIsSelling)
                    .andThen(OrderProductValidator.validateECouponPrd)
                    .andThen(OrderProductValidator.validateECouponDelivery),
            OrderDeliveryValidator.generalParamPredicate
                    .and(OrderDeliveryValidator.eCouponParamPredicate)
                    .and(OrderDeliveryValidator.eCoupondeliveryCntPredicate)
    );

    private final String systemTypeCode;
    private final String orderTypeCode;
    private final Consumer<ProductInfoVO> productInfoVOPredicate;
    private final Predicate<OrderRequestVO> deliveryPredicate;

    public void isValid(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        isValidOrderBasic(orderRequestVO);
        isValidDelivery(orderRequestVO);
        isValidOrdPrd(orderProductViewList);
    }

    private void isValidOrderBasic(OrderRequestVO orderRequestVO){
        boolean containsVacctPayment = orderRequestVO.isContainsVacctPayment();
        if(!containsVacctPayment) return;

        boolean isValid = OrderPaymentValidator.vacctPredicate.test(orderRequestVO.getOrderBasicVO());
        if(!isValid){
            throw new ValidationException(OrderException.INVALID_ORDER_TPYE.msg);
        }
    }

    private void isValidDelivery(OrderRequestVO orderRequestVO){
        boolean isValid = deliveryPredicate.test(orderRequestVO);
        if(!isValid){
            throw new ValidationException(OrderException.INVALID_ORDER.msg);
        }
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
