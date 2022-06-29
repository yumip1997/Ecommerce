package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.apply.vo.ApplicablePdCupVO;
import com.plateer.ec1.promotion.apply.vo.PrmApplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ProductCouponResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j2
public class ProductCouponCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<ApplicablePdCupVO> applicablePdCupVOList = prmApplyMapper.getApplicablePdCupList(prmRequestBaseVO);

        if(!CollectionUtils.isEmpty(applicablePdCupVOList)){
            calculate(applicablePdCupVOList);
        }

        return ProductCouponResponseVO.builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .applicablePdCupVOList(applicablePdCupVOList)
                .build();
    }

    private void calculate(List<ApplicablePdCupVO> applicablePdCupVOList){
        for (ApplicablePdCupVO applicablePdCupVO : applicablePdCupVOList) {
            //혜택금액 셋팅
            setBnfVal(applicablePdCupVO);

            //최대혜택 쿠폰 "Y"로 셋팅
            PrmApplyVO maxBnfPrm = getMaxBenefitPrm(applicablePdCupVO.getPrmApplyVOList());
            maxBnfPrm.setMaxBenefitYn("Y");
        }

    }

    @Override
    public PRM0004Code getType() {
        return PRM0004Code.PROUDCT_COUPON;
    }

}
