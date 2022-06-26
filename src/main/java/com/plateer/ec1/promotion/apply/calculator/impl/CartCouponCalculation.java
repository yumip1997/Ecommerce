package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.apply.calculator.Calculation;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.CartCouponResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CartCouponCalculation implements Calculation {

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        log.info("장바구니 할인금액 계산");
        return new CartCouponResponseVO();
    }

    @Override
    public PromotionType getType() {
        return PromotionType.CART_COUPON;
    }

}
