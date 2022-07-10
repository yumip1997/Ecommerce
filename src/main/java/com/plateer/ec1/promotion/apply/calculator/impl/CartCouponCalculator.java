package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.common.enums.CommonConstants;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartCouponCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<PrmCartAplyVO> prmCartAplyVOList = prmApplyMapper.getApplicableCartCupList(prmRequestBaseVO);
        calculate((prmCartAplyVOList));

        return PrmResponseVO.<PrmCartAplyVO>builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .list(prmCartAplyVOList)
                .build();
    }

    private void calculate(List<PrmCartAplyVO> prmCartAplyVOList) {
        for (PrmCartAplyVO prmCartAplyVO : prmCartAplyVOList) {
            ApplicablePrmVO applicablePrmVO = prmCartAplyVO.getApplicablePrmVO();

            Long bnfVal = getBnfVal(applicablePrmVO, prmCartAplyVO.getOrdAmt());
            applicablePrmVO.setBnfVal(bnfVal);
        }

        setMaxBenefitOfPrmCartAplyVO(prmCartAplyVOList);
    }

    private void setMaxBenefitOfPrmCartAplyVO(List<PrmCartAplyVO> prmCartAplyVOList){
        if(CollectionUtils.isEmpty(prmCartAplyVOList)) return;

        List<ApplicablePrmVO> applicablePrmVOList = extractApplicablePrmVOList(prmCartAplyVOList);
        Optional<ApplicablePrmVO> maxBnfPrmOpt = getMaxBenefitPrm(applicablePrmVOList);

        maxBnfPrmOpt.ifPresent(applicablePrmVO -> applicablePrmVO.setMaxBenefitYn(CommonConstants.Y.getCode()));
    }

    private List<ApplicablePrmVO> extractApplicablePrmVOList(List<PrmCartAplyVO> prmCartAplyVOList){
        return prmCartAplyVOList.stream()
                .map(PrmCartAplyVO::getApplicablePrmVO)
                .collect(Collectors.toList());
    }

    @Override
    public PrmTypeCode getType() {
        return PrmTypeCode.CART_COUPON;
    }

}
