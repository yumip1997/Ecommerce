package com.plateer.ec1.promotion.apply.service;

import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.CartCouponResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.PriceDiscountResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ProductCouponResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrmApplyServiceTest {

    @Autowired
    private PrmApplyService prmApplyService;

    @Test
    @DisplayName("가격조정 프로모션 요청 시 PriceDiscountResponseVO 타입의 객체가 반환된다.")
    void price_distcount_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().cpnKindCd("10").build();

        ResponseBaseVO responseBaseVO = prmApplyService.getCalculationData(prmRequestBaseVO);

        Assertions.assertThat(responseBaseVO).isInstanceOf(PriceDiscountResponseVO.class);
    }

    @Test
    @DisplayName("상품쿠폰 프로모션 요청 시 ProductCouponResponseVO 타입의 객체가 반환된다.")
    void product_coupon_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().cpnKindCd("20").build();

        ResponseBaseVO responseBaseVO = prmApplyService.getCalculationData(prmRequestBaseVO);

        Assertions.assertThat(responseBaseVO).isInstanceOf(ProductCouponResponseVO.class);
    }

    @Test
    @DisplayName("장바구니 프로모션 요청 시 CartCouponResponseVO 타입의 객체가 반환된다.")
    void cart_coupon_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().cpnKindCd("30").build();

        ResponseBaseVO responseBaseVO = prmApplyService.getCalculationData(prmRequestBaseVO);

        Assertions.assertThat(responseBaseVO).isInstanceOf(CartCouponResponseVO.class);
    }

}