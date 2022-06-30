package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.ApplicableCupVO;
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
public class ProductCouponCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<PrmAplyVO> prmAplyVOList = prmApplyMapper.getApplicablePrmList(prmRequestBaseVO);
        calculate(prmAplyVOList);

        return ProductCouponResponseVO.builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .prmAplyVOList(prmAplyVOList)
                .build();
    }

    @Override
    public void calculate(List<PrmAplyVO> prmAplyVOList){
        for (PrmAplyVO prmAplyVO : prmAplyVOList) {
            List<ApplicableCupVO> applicableCupVOList = prmAplyVO.getApplicableCupVOList();
            if(CollectionUtils.isEmpty(applicableCupVOList)) continue;

            setBnfVal(prmAplyVO);

            ApplicableCupVO maxBnfPrm = getMaxBenefitPrm(applicableCupVOList);
            maxBnfPrm.setMaxBenefitYn("Y");
        }

    }

    @Override
    public PRM0004Code getType() {
        return PRM0004Code.PROUDCT_COUPON;
    }

}
