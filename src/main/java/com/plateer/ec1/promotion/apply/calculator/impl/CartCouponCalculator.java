package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.CartCouponResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class CartCouponCalculator implements Calculator {

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        log.info("장바구니 할인금액 계산");
        return CartCouponResponseVO.builder().build();
    }

    @Override
    public void calculate(List<PrmAplyVO> prmAplyVOList) {

    }

    @Override
    public PRM0004Code getType() {
        return PRM0004Code.CART_COUPON;
    }

}
