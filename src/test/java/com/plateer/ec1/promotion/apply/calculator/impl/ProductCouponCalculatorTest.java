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
                .orElse(null);
    }

    public PrmAplyVO findPrmAply(List<PrmAplyVO> prmAplyVOList, String goodsNo, String itemNo){
        return prmAplyVOList.stream()
                .filter(e -> e.getProductInfoVO().getGoodsNo().equals(goodsNo)
                        && e.getProductInfoVO().getItemNo().equals(itemNo))
                .findFirst()
                .orElse(null);
    }

    public ProductInfoVO getPrd(String goodsNo, String itemNo, Long orrAt){
        return ProductInfoVO.builder().goodsNo(goodsNo).itemNo(itemNo).orrAt(orrAt).build();
    }

    public ProductInfoVO getPrd(String goodsNo, String itemNo, Long orrAt, Long appliedCpnIssNo){
        return ProductInfoVO.builder().goodsNo(goodsNo).itemNo(itemNo).orrAt(orrAt).appliedCpnIssNo(appliedCpnIssNo).build();
    }

    //test01이 P001 - 1, P002 - 1 을 주문
    @Test
    @DisplayName("상품 P001의 단품1, P002의 단품1의 적용가능 상품 쿠폰, 최대혜택 쿠폰의 발급번호 테스트")
    void test01_TEST(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("test01").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(
                getPrd("P001", "1", 29900L),
                getPrd("P002", "1", 10250L))
        );
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        List<ApplicablePrmVO> applicablePrmVOList = prmAplyVOList.get(0).getApplicablePrmVOList();
        Assertions.assertThat(applicablePrmVOList.size()).isEqualTo(1);

        ApplicablePrmVO maxBnf = getMaxBnfPrm(applicablePrmVOList);
        Assertions.assertThat(maxBnf.getCpnIssNo()).isEqualTo(1L);
        Assertions.assertThat(maxBnf.getPrmNo()).isEqualTo(1L);

    }

    //test02 P001-1, P001-2, P002-1, P002, P005-1, P005-2, P005-3, P006-0, P007-1, P007-1, P00-2, P00-3을 주문

    @Test
    @DisplayName("test02가 P001-1, P001-2, P002-1, P002-2, P005-1, P005-2, P005-3, P006-0, P007-1, P007-1, P070-2, P007-3을 주문")
    public void test2_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("test02").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(
                getPrd("P001", "1", 29000L),
                getPrd("P001", "2", 29000L),
                getPrd("P002", "1", 10250L),
                getPrd("P002", "2", 10250L),
                getPrd("P005", "1", 9000L),
                getPrd("P005", "2", 9000L),
                getPrd("P005", "3", 9000L),
                getPrd("P006", "0", 140000L),
                getPrd("P007", "1", 24000L),
                getPrd("P007", "2", 24000L),
                getPrd("P007", "3", 24000L)));

        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        //상품 P001 단품 1의 최대할인쿠폰 프로모션번호 3 / 쿠폰발급번호 7
        PrmAplyVO p001_1 = findPrmAply(prmAplyVOList, "P001", "1");
        ApplicablePrmVO maxBnfPrmOfp001_1 = getMaxBnfPrm(p001_1.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp001_1.getPrmNo()).isEqualTo(3L);
        Assertions.assertThat(maxBnfPrmOfp001_1.getCpnIssNo()).isEqualTo(7L);

        //상품 P001 단품 2의 최대할인쿠폰 프로모션번호 3 / 쿠폰발급번호 8
        PrmAplyVO p001_2 = findPrmAply(prmAplyVOList, "P001", "2");
        ApplicablePrmVO maxBnfPrmOfp001_2 = getMaxBnfPrm(p001_2.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp001_2.getPrmNo()).isEqualTo(3L);
        Assertions.assertThat(maxBnfPrmOfp001_2.getCpnIssNo()).isEqualTo(8L);

        //상품 P002 단품 1의 최대할인쿠폰 프로모션번호 3 / 쿠폰발급번호 9
        PrmAplyVO p002_1 = findPrmAply(prmAplyVOList, "P002", "1");
        ApplicablePrmVO maxBnfPrmOfpp002_1 = getMaxBnfPrm(p002_1.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfpp002_1.getPrmNo()).isEqualTo(3L);
        Assertions.assertThat(maxBnfPrmOfpp002_1.getCpnIssNo()).isEqualTo(9L);

        //상품 P002 단품 2의 최대할인쿠폰 - 없음 (프로모션번호 3에 대해 가능한데 앞의 상품에서 다 사용함)
        PrmAplyVO p002_2 = findPrmAply(prmAplyVOList, "P002", "2");
        ApplicablePrmVO maxBnfPrmOfpp002_2 = getMaxBnfPrm(p002_2.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfpp002_2).isNull();

        //상품 P005 단품 1의 최대할인쿠폰 - 없음 (프로모션번호 3에 대해 가능한데 앞의 상품에서 다 사용함)
        PrmAplyVO p005_1 = findPrmAply(prmAplyVOList, "P005", "1");
        ApplicablePrmVO maxBnfPrmOfp005_1 = getMaxBnfPrm(p005_1.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp005_1).isNull();

        //상품 P005 단품 2의 최대할인쿠폰 - 없음 (프로모션번호 3에 대해 가능한데 앞의 상품에서 다 사용함)
        PrmAplyVO p005_2 = findPrmAply(prmAplyVOList, "P005", "2");
        ApplicablePrmVO maxBnfPrmOfp005_2 = getMaxBnfPrm(p005_2.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp005_2).isNull();

        //상품 P005 단품 3의 최대할인쿠폰 - 없음 (프로모션번호 3에 대해 가능한데 앞의 상품에서 다 사용함)
        PrmAplyVO p005_3 = findPrmAply(prmAplyVOList, "P005", "3");
        ApplicablePrmVO maxBnfPrmOfp005_3 = getMaxBnfPrm(p005_3.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp005_3).isNull();

        //상품 P006 단품 0의 최대할인쿠폰 프로모션번호 4 / 쿠폰발급번호 10
        PrmAplyVO p006_0 = findPrmAply(prmAplyVOList, "P006", "0");
        ApplicablePrmVO maxBnfPrmOfp006_0 = getMaxBnfPrm(p006_0.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp006_0.getPrmNo()).isEqualTo(4L);
        Assertions.assertThat(maxBnfPrmOfp006_0.getCpnIssNo()).isEqualTo(10L);

        //상품 P007 단품 1의 최대할인쿠폰 프로모션번호 5 / 쿠폰발급번호 11
        PrmAplyVO p007_1 = findPrmAply(prmAplyVOList, "P007", "1");
        ApplicablePrmVO maxBnfPrmOfp007_1 = getMaxBnfPrm(p007_1.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp007_1.getPrmNo()).isEqualTo(5L);
        Assertions.assertThat(maxBnfPrmOfp007_1.getCpnIssNo()).isEqualTo(11L);

        //상품 P007 단품 2의 최대할인쿠폰 - 없음 (프로모션번호 5에 대해 가능한데 앞의 상품에서 다 사용함)
        PrmAplyVO p007_2 = findPrmAply(prmAplyVOList, "P007", "2");
        ApplicablePrmVO maxBnfPrmOfp007_2 = getMaxBnfPrm(p007_2.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp007_2).isNull();

        //상품 P007 단품 3의 최대할인쿠폰 - 없음 (프로모션번호 5에 대해 가능한데 앞의 상품에서 다 사용함)
        PrmAplyVO p007_3 = findPrmAply(prmAplyVOList, "P007", "3");
        ApplicablePrmVO maxBnfPrmOfp007_3 = getMaxBnfPrm(p007_3.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrmOfp007_3).isNull();
    }


    @Test
    @DisplayName("P100상품의 단품1에 적용가능한 상품쿠폰 - 없음")
    public void empty_test() {
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("test01").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        ProductInfoVO productInfoVO1 = ProductInfoVO.builder().goodsNo("P100").itemNo("1").orrAt(5000L).build();
        productInfoVOList.add(productInfoVO1);
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        Assertions.assertThat(prmAplyVOList).isEmpty();
    }

    @Test
    @DisplayName("적용되어 있는 쿠폰이 있을 경우 적용가능한 상품쿠폰, 최대혜택 테스트")
    public void applied_test(){
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("10").mbrNo("test02").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(
                getPrd("P001", "1", 1000L, null),
                getPrd("P001", "2", 1000L, null),
                getPrd("P001", "3", 1000L, 3L))
        );
        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);

        PrmResponseVO<PrmAplyVO> calculationData = (PrmResponseVO<PrmAplyVO>) productCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = calculationData.getList();

        PrmAplyVO p001 = findPrmAply(prmAplyVOList, "P001", "1");
        ApplicablePrmVO maxBnfPrm1 = getMaxBnfPrm(p001.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrm1.getCpnIssNo()).isEqualTo(4L);

        PrmAplyVO p002 = findPrmAply(prmAplyVOList, "P001", "2");
        ApplicablePrmVO maxBnfPrm2 = getMaxBnfPrm(p002.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrm2).isNull();

        PrmAplyVO p003 = findPrmAply(prmAplyVOList, "P001", "3");
        ApplicablePrmVO maxBnfPrm3 = getMaxBnfPrm(p003.getApplicablePrmVOList());
        Assertions.assertThat(maxBnfPrm3.getCpnIssNo()).isEqualTo(3L);

    }


}