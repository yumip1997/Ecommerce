package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.vo.request.PrmRequestBase;
import com.plateer.ec1.promotion.vo.response.CartCouponResponseVO;
import com.plateer.ec1.promotion.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class CartCouponCalculation implements Calculation {

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBase prmRequestBase) {
        log.info("장바구니 할인금액 계산");
        return new CartCouponResponseVO();
    }

    @Override
    public PromotionType getType() {
        return PromotionType.CART_COUPON;
    }

}
