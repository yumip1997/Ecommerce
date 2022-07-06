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


    //test01이 P001 - 1, P002 - 1 을 주문
    //최대혜택 상품 쿠폰을 적용
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

    //최대혜택 상품 쿠폰을 적용
    @Test
    @DisplayName("test02가 P001-1, P001-2, P002-1, P002-2, P005-1, P005-2, P005-3, P006-0, P007-1, P007-1, P070-2, P007-3을 주문")
    public void test2_test() {
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder().prmKindCd("20").cpnKindCd("30").mbrNo("test02").build();
        List<ProductInfoVO> productInfoVOList = new ArrayList<>(Arrays.asList(
                getPrd("P001", "1", 24650L, 1L),
                getPrd("P001", "2", 24650L, 2L),
                getPrd("P002", "1", 8713L, 2L),
                getPrd("P002", "2", 10250L, 1L),
                getPrd("P005", "1", 9000L, 1L),
                getPrd("P005", "2", 9000L, 1L),
                getPrd("P005", "3", 9000L, 3L),
                getPrd("P006", "0", 137000L, 1L),
                getPrd("P007", "1", 21000L, 1L),
                getPrd("P007", "2", 24000L, 2L),
                getPrd("P007", "3", 24000L, 1L)));

        prmRequestBaseVO.setProductInfoVOList(productInfoVOList);
        PrmResponseVO<PrmCartAplyVO> calculationData = (PrmResponseVO<PrmCartAplyVO>) cartCouponCalculator.getCalculationData(prmRequestBaseVO);
        List<ApplicablePrmVO> collect = calculationData.getList().stream().map(PrmCartAplyVO::getApplicablePrmVO).collect(Collectors.toList());

        ApplicablePrmVO maxBnfPrm = getMaxBnfPrm(collect);
        Assertions.assertThat(maxBnfPrm.getPrmNo()).isEqualTo(6L);
        Assertions.assertThat(maxBnfPrm.getCpnIssNo()).isEqualTo(12L);
        Assertions.assertThat(maxBnfPrm.getBnfVal()).isEqualTo(22000L);
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