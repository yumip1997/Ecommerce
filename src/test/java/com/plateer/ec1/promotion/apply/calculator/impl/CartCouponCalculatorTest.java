package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.PrmCartAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PrmResponseVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class CartCouponCalculatorTest {

    @Autowired
    private CartCouponCalculator cartCouponCalculator;

    public ApplicablePrmVO getMaxBnfPrm(List<ApplicablePrmVO> applicablePrmVOList){
        return applicablePrmVOList.stream()
                .filter(e -> "Y".equals(e.getMaxBenefitYn()))
                .findFirst()
                .orElse(null);
    }

    public ProductInfoVO getPrd(String goodsNo, String itemNo, Long orrAt, Long orrCnt){
        return ProductInfoVO.builder().goodsNo(goodsNo).itemNo(itemNo).orrAt(orrAt).orrCnt(orrCnt).build();
    }


    ////test01이 P001 - 1, P002 - 1 을 주문
    //P001-1에는 상품쿠폰 1000원을 적용 -> 주문금액은 29000-1000 = 28000
    @Test
    @DisplayName("test01이 P001 - 1, P002 - 1 을 주문")
    public void test01_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("30").mbrNo("test01").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(
                getPrd("P001", "1", 28000L, 2L),
                getPrd("P002", "1", 10250L, 3L))
        );
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmCartAplyVO> calculationData = (PrmResponseVO) cartCouponCalculator.getCalculationData(prmRequestBaseVO);

        List<PrmCartAplyVO> list = calculationData.getList();
        ApplicablePrmVO applicablePrmVO = list.get(0).getApplicablePrmVO();
        Assertions.assertThat(applicablePrmVO.getPrmNo()).isEqualTo(2L);
        Assertions.assertThat(applicablePrmVO.getCpnIssNo()).isEqualTo(2L);
    }


    @Test
    @DisplayName("적용가능 장바구니 쿠폰 없을 경우")
    public void empty_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("30").mbrNo("user1").build();
        ProductInfoVO p100 = ProductInfoVO.builder().goodsNo("P100").itemNo("1").orrCnt(2L).orrAt(5000L).build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(p100));
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmCartAplyVO> calculationData = (PrmResponseVO) cartCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmCartAplyVO> list = calculationData.getList();
        Assertions.assertThat(list).isEmpty();

    }
}