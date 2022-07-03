package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.PdPrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmCartAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PrmResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import com.plateer.ec1.promotion.enums.PrmTypeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
public class CartCouponCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        //상품-단품-프로모션-발급번호 단위로 row가 형성됨
        List<PdPrmVO> pdPrmVOList = prmApplyMapper.getApplicablePrmList(prmRequestBaseVO);
        List<PrmCartAplyVO> prmCartAplyVOList = groupByApplicableCup(pdPrmVOList);
        calculate((prmCartAplyVOList));

        return PrmResponseVO.<PrmCartAplyVO>builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .list(prmCartAplyVOList)
                .build();
    }

    //TODO RETURN 값 조작 방식 고민해보기
    //프로모션발급번호로 그룹핑한다 (프로모션1 - 상품N)
    public List<PrmCartAplyVO> groupByApplicableCup(List<PdPrmVO> pdPrmVOList){
        Map<String, List<PdPrmVO>> collect = pdPrmVOList.stream().collect(groupingBy(PdPrmVO::getPrmCupIssNo));
        return collect.entrySet().stream()
                .map(this::convertPrmCartAplyVO)
                .filter(this::filterByPriceWithCnt)
                .collect(Collectors.toList());
    }

    private PrmCartAplyVO convertPrmCartAplyVO(Map.Entry<String, List<PdPrmVO>> entry){
        ApplicablePrmVO applicablePrmVO = entry.getValue().stream().map(PdPrmVO::getApplicablePrmVO).findFirst().orElse(ApplicablePrmVO.builder().build());
        List<ProductInfoVO> productInfoVOList = entry.getValue().stream().map(PdPrmVO::getProductInfoVO).collect(Collectors.toList());

        return PrmCartAplyVO.builder().applicablePrmVO(applicablePrmVO).productInfoVOList(productInfoVOList).build();
    }

    private boolean filterByPriceWithCnt(PrmCartAplyVO prmCartAplyVO){
        Long prdTotalPrice = applicablePrdTotalPriceWithCnt(prmCartAplyVO.getProductInfoVOList());
        Long minPrice = prmCartAplyVO.getApplicablePrmVO().getMinPurAmt();

        return minPrice <= prdTotalPrice;
    }

    private Long applicablePrdTotalPriceWithCnt(List<ProductInfoVO> productInfoVOList){
        return productInfoVOList.stream()
                .mapToLong(e -> e.getOrrAt() * e.getOrrCnt())
                .sum();
    }

    private void calculate(List<PrmCartAplyVO> prmCartAplyVOList) {
        for (PrmCartAplyVO prmCartAplyVO : prmCartAplyVOList) {
            ApplicablePrmVO applicablePrmVO = prmCartAplyVO.getApplicablePrmVO();
            Long prdTotalPrice = applicablePrdTotalPriceWithCnt(prmCartAplyVO.getProductInfoVOList());

            Long bnfVal = getBnfVal(prdTotalPrice, applicablePrmVO);
            applicablePrmVO.setBnfVal(bnfVal);
        }

        setMaxBenefitOfPrmCartAplyVO(prmCartAplyVOList);
    }

    private void setMaxBenefitOfPrmCartAplyVO(List<PrmCartAplyVO> prmCartAplyVOList){
        if(CollectionUtils.isEmpty(prmCartAplyVOList)) return;

        ApplicablePrmVO applicablePrmVO = getMaxBenefitPrm(extractApplicableCupVOList(prmCartAplyVOList));
        applicablePrmVO.setMaxBenefitYn("Y");
    }

    private List<ApplicablePrmVO> extractApplicableCupVOList(List<PrmCartAplyVO> prmCartAplyVOList){
        return prmCartAplyVOList.stream()
                .map(PrmCartAplyVO::getApplicablePrmVO)
                .collect(Collectors.toList());
    }

    @Override
    public PrmTypeCode getType() {
        return PrmTypeCode.CART_COUPON;
    }

}
