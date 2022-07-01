package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PrmResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ProductCouponCalculatorTest {

    @Autowired
    private ProductCouponCalculator productCouponCalculator;

    @Test
    @DisplayName("P001상품의 단품1에 적용가능한 상품쿠폰(1 - 1000원 할인,3 - 750원 할인, 9 - 1000원 할인) 테스트")
    public void p001_1_test() {
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("user1").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        ProductInfoVO productInfoVO1 = ProductInfoVO.builder().goodsNo("P001").itemNo("1").prmPrc(5000L).salePrc(5000L).orrAt(5000L).build();
        productInfoVOList.add(productInfoVO1);
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        for (PrmAplyVO prmAplyVO : prmAplyVOList) {
            List<ApplicablePrmVO> applicablePrmVOList = prmAplyVO.getApplicablePrmVOList();

            ApplicablePrmVO maxBnf =
                    applicablePrmVOList.stream().filter(e -> "Y".equals(e.getMaxBenefitYn())).findFirst().orElse(ApplicablePrmVO.builder().build());

            //최대혜택 쿠폰은 쿠폰1, 쿠폰9 -> 쿠폰1이 우선
            //user1은 쿠폰1을 2번 다운받음 -> 먼저 발급받은 쿠폰을 우선
            Assertions.assertThat(maxBnf.getCpnIssNo()).isEqualTo(1L);
            Assertions.assertThat(maxBnf.getPrmNo()).isEqualTo(1L);
        }
    }


}