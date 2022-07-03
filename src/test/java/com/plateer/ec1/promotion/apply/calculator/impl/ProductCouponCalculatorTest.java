package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
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

@SpringBootTest
class ProductCouponCalculatorTest {

    @Autowired
    private ProductCouponCalculator productCouponCalculator;

    public ApplicablePrmVO getMaxBnfPrm(List<ApplicablePrmVO> applicablePrmVOList){
        return applicablePrmVOList.stream()
                .filter(e -> "Y".equals(e.getMaxBenefitYn()))
                .findFirst()
                .orElse(ApplicablePrmVO.builder().build());
    }

    public ProductInfoVO getPrd(String goodsNo, String itemNo){
        return ProductInfoVO.builder().goodsNo(goodsNo).itemNo(itemNo).orrAt(5000L).build();
    }

    //상품 P001에 적용가능한 쿠폰 (6개)
    //쿠폰번호 1 -> 2번 발급받음(2장)- 발급번호 1, 발급번호2 - 최대혜택 (1000원)
    //쿠폰번호 3 -> 2번 발급받음(2장)- 발급번호 3, 발급번호4 - 혜택 (750원)
    //쿠폰번호 9 -> 2번 발급받음(2장) - 발급번호 5, 발급번호6 - 최대혜택 (1000원)

    //상품 P001 - 단품1
    @Test
    @DisplayName("상품 P001의 단품1에 적용가능한 상품쿠폰, 최대혜택 쿠폰 & 적용가능 발급번호")
    public void p001_1_test() {
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("user1").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(getPrd("P001", "1")));
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        for (PrmAplyVO prmAplyVO : prmAplyVOList) {
            List<ApplicablePrmVO> applicablePrmVOList = prmAplyVO.getApplicablePrmVOList();

            ApplicablePrmVO maxBnf = getMaxBnfPrm(applicablePrmVOList);

            //최대혜택 쿠폰은 쿠폰1, 쿠폰9 -> 쿠폰1이 우선
            //user1은 쿠폰1을 2번(발급번호1, 발급번호2) 다운받음 -> 먼저 발급받은 쿠폰을 우선
            Assertions.assertThat(maxBnf.getCpnIssNo()).isEqualTo(1L);
            Assertions.assertThat(maxBnf.getPrmNo()).isEqualTo(1L);
        }
    }


    @Test
    @DisplayName("P001상품의 단품1, 단품2에 적용가능한 상품쿠폰, 최대혜택 쿠폰 & 적용가능 발급번호")
    public void p001_1_2test() {
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("user1").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(getPrd("P001", "1"), getPrd("P001", "2")));
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        List<ApplicablePrmVO> applicablePrmVOList1 = prmAplyVOList.get(0).getApplicablePrmVOList();
        ApplicablePrmVO maxBnf = getMaxBnfPrm(applicablePrmVOList1);
        Assertions.assertThat(applicablePrmVOList1.size()).isEqualTo(6);
        Assertions.assertThat(maxBnf.getPrmNo()).isEqualTo(1L);
        Assertions.assertThat(maxBnf.getCpnIssNo()).isEqualTo(1L);

        List<ApplicablePrmVO> applicablePrmVOList2 = prmAplyVOList.get(1).getApplicablePrmVOList();
        ApplicablePrmVO maxBnf2 = getMaxBnfPrm(applicablePrmVOList2);
        Assertions.assertThat(applicablePrmVOList1.size()).isEqualTo(6);
        Assertions.assertThat(maxBnf2.getPrmNo()).isEqualTo(1L);
        Assertions.assertThat(maxBnf2.getCpnIssNo()).isEqualTo(2L);
    }

    @Test
    @DisplayName("P001상품의 단품1, 단품, 단품3에 상품쿠폰, 최대혜택 쿠폰 & 적용가능 발급번호")
    public void p001_1_2_3test() {
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("user1").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(getPrd("P001", "1"),
                getPrd("P001", "2"),
                getPrd("P001", "3")));
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        List<ApplicablePrmVO> applicablePrmVOList1 = prmAplyVOList.get(0).getApplicablePrmVOList();
        ApplicablePrmVO maxBnf = getMaxBnfPrm(applicablePrmVOList1);
        Assertions.assertThat(applicablePrmVOList1.size()).isEqualTo(6);
        Assertions.assertThat(maxBnf.getPrmNo()).isEqualTo(1L);
        Assertions.assertThat(maxBnf.getCpnIssNo()).isEqualTo(1L);

        List<ApplicablePrmVO> applicablePrmVOList2 = prmAplyVOList.get(1).getApplicablePrmVOList();
        ApplicablePrmVO maxBnf2 = getMaxBnfPrm(applicablePrmVOList2);
        Assertions.assertThat(applicablePrmVOList2.size()).isEqualTo(6);
        Assertions.assertThat(maxBnf2.getPrmNo()).isEqualTo(1L);
        Assertions.assertThat(maxBnf2.getCpnIssNo()).isEqualTo(2L);

        List<ApplicablePrmVO> applicablePrmVOList3 = prmAplyVOList.get(2).getApplicablePrmVOList();
        ApplicablePrmVO maxBnf3 = getMaxBnfPrm(applicablePrmVOList3);
        Assertions.assertThat(applicablePrmVOList3.size()).isEqualTo(6);
        Assertions.assertThat(maxBnf3.getPrmNo()).isEqualTo(9L);
        Assertions.assertThat(maxBnf3.getCpnIssNo()).isEqualTo(5L);
    }

    @Test
    @DisplayName("P100상품의 단품1에 적용가능한 상품쿠폰 - 없음")
    public void empty_test() {
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("user1").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        ProductInfoVO productInfoVO1 = ProductInfoVO.builder().goodsNo("P100").itemNo("1").orrAt(5000L).build();
        productInfoVOList.add(productInfoVO1);
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        Assertions.assertThat(prmAplyVOList).isEmpty();
    }


}