package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.common.enums.CommonConstants;
import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmCartAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PrmResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import com.plateer.ec1.promotion.enums.PrmTypeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartCouponCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<PrmCartAplyVO> prmCartAplyVOList = getPrmCartAplyVOList(prmRequestBaseVO);
        calculate((prmCartAplyVOList));

        return PrmResponseVO.<PrmCartAplyVO>builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .list(prmCartAplyVOList)
                .build();
    }

    private List<PrmCartAplyVO> getPrmCartAplyVOList(PrmRequestBaseVO prmRequestBaseVO){
        List<PrmCartAplyVO> prmCartAplyVOList = prmApplyMapper.getApplicableCartCupList(prmRequestBaseVO);
        return prmCartAplyVOList.stream()
                .filter(this::filterByPriceWithCnt)
                .collect(Collectors.toList());
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
        applicablePrmVO.setMaxBenefitYn(CommonConstants.Y.getCode());
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
