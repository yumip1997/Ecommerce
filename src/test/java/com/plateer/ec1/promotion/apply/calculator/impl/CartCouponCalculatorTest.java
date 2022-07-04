package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
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
                .orElse(ApplicablePrmVO.builder().build());
    }

    //주문서에 담긴 상품 P002 - 단품1 - 1만원 3개 구매
    //적용가능 장바구니 쿠폰
    //쿠폰 2번 - P001 - 최대 1000원 할인
    //쿠폰 8번 - P002 - 최대 5000원 할인 ->  기간만료!
    @Test
    @DisplayName("상품 P002의 단품1의 적용가능 장바구니 쿠폰")
    public void P002_1_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("30").mbrNo("user1").build();
        ProductInfoVO p002 = ProductInfoVO.builder().goodsNo("P002").itemNo("1").orrCnt(3L).orrAt(10000L).build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(p002));
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmCartAplyVO> calculationData = (PrmResponseVO) cartCouponCalculator.getCalculationData(prmRequestBaseVO);

        //3만원의 10%는 3천원이지만 최대할인이 1천이기에 할인금액은 1천원
        Long bnfVal = calculationData.getList().get(0).getApplicablePrmVO().getBnfVal();
        Assertions.assertThat(bnfVal).isEqualTo(1000L);

        List<ApplicablePrmVO> collect = calculationData.getList().stream().map(PrmCartAplyVO::getApplicablePrmVO).collect(Collectors.toList());
        ApplicablePrmVO maxBnfPrm = getMaxBnfPrm(collect);
        Assertions.assertThat(maxBnfPrm.getPrmNo()).isEqualTo(2L);
    }

    //주문서에 담긴 상품 P001-단품1, P002-단품1, P003-단품1
    //적용가능 장바구니 쿠폰
    //쿠폰 2번(최대 1천원) - P001, P002
    //쿠폰 8번(최대 1천원) - P002 => 기간만료!
    @Test
    @DisplayName("상품 P001의 단품1의 적용가능 장바구니 쿠폰")
    public void p001_1_p002_1_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("30").mbrNo("user1").build();
        ProductInfoVO p001 = ProductInfoVO.builder().goodsNo("P001").itemNo("1").orrCnt(1L).orrAt(10000L).build();
        ProductInfoVO p002 = ProductInfoVO.builder().goodsNo("P002").itemNo("1").orrCnt(1L).orrAt(10000L).build();
        ProductInfoVO p003 = ProductInfoVO.builder().goodsNo("P003").itemNo("1").orrCnt(1L).orrAt(20000L).build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(p001, p002, p003));
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmCartAplyVO> calculationData = (PrmResponseVO) cartCouponCalculator.getCalculationData(prmRequestBaseVO);
        Long bnfVal = calculationData.getList().get(0).getApplicablePrmVO().getBnfVal();
        Assertions.assertThat(bnfVal).isEqualTo(1000L);

        List<ApplicablePrmVO> collect = calculationData.getList().stream().map(PrmCartAplyVO::getApplicablePrmVO).collect(Collectors.toList());
        ApplicablePrmVO maxBnfPrm = getMaxBnfPrm(collect);
        Assertions.assertThat(maxBnfPrm.getPrmNo()).isEqualTo(2L);
    }

    //주문서에 담긴 상품 P005-단품1, P006-단품1, P007-단품1
    //최소구매금액 조건에 만족하지 않을경우
    @Test
    @DisplayName("최소구매금액 조건 만족X, 상품 P005 단품1, P006 단품1, P007 단품1에 적용가능한 장바구니 쿠폰")
    public void not_min_pirce_p005_1_p006_1_p007_1_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("30").mbrNo("user1").build();
        ProductInfoVO p005 = ProductInfoVO.builder().goodsNo("P005").itemNo("1").orrCnt(1L).orrAt(5000L).build();
        ProductInfoVO p006 = ProductInfoVO.builder().goodsNo("P006").itemNo("1").orrCnt(1L).orrAt(5000L).build();
        ProductInfoVO p007 = ProductInfoVO.builder().goodsNo("P007").itemNo("1").orrCnt(1L).orrAt(5000L).build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(p005, p006, p007));
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmCartAplyVO> calculationData = (PrmResponseVO) cartCouponCalculator.getCalculationData(prmRequestBaseVO);
        Assertions.assertThat(calculationData.getList()).isEmpty();

    }

    //주문서에 담긴 상품 P005-단품1, P006-단품1, P007-단품1
    //쿠폰 6(최소구매금액 3만원, 8%할인, 최대 1만원 할인) - P005, P006, P007 -> 수량 합 총 3만원 -> 0
    //쿠폰 7(최소구매금액 3만원) - P007 -> 수량 합 총 1만원 -> X
    @Test
    @DisplayName("최소구매금액 조건 만족X, 상품 P005 단품1, P006 단품1, P007 단품1에 적용가능한 장바구니 쿠폰")
    public void p005_1_p006_1_p007_1_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("30").mbrNo("user1").build();
        ProductInfoVO p005 = ProductInfoVO.builder().goodsNo("P005").itemNo("1").orrCnt(2L).orrAt(5000L).build();
        ProductInfoVO p006 = ProductInfoVO.builder().goodsNo("P006").itemNo("1").orrCnt(2L).orrAt(5000L).build();
        ProductInfoVO p007 = ProductInfoVO.builder().goodsNo("P007").itemNo("1").orrCnt(2L).orrAt(5000L).build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(p005, p006, p007));
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmCartAplyVO> calculationData = (PrmResponseVO) cartCouponCalculator.getCalculationData(prmRequestBaseVO);
        Long bnfVal = calculationData.getList().get(0).getApplicablePrmVO().getBnfVal();
        Assertions.assertThat(bnfVal).isEqualTo(2400L);

        Long prmNo = calculationData.getList().get(0).getApplicablePrmVO().getPrmNo();
        Assertions.assertThat(prmNo).isEqualTo(6L);

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