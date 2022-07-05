package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.PdPrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PrmResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import com.plateer.ec1.promotion.enums.PrmTypeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Component
public class ProductCouponCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<PrmAplyVO> prmAplyVOList = prmApplyMapper.getApplicablePdCupList(prmRequestBaseVO);
        calculate(prmAplyVOList);

        return PrmResponseVO.<PrmAplyVO>builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .list(prmAplyVOList)
                .build();
    }

    private void calculate(List<PrmAplyVO> prmAplyVOList){
        Set<Long> maxBnfSet = new HashSet<>();

        for (PrmAplyVO prmAplyVO : prmAplyVOList) {
            List<ApplicablePrmVO> applicablePrmVOList = prmAplyVO.getApplicablePrmVOList();
            if(CollectionUtils.isEmpty(applicablePrmVOList)) continue;

            setBnfVal(applicablePrmVOList, prmAplyVO.getProductInfoVO().getOrrAt());
            setupMaxBenefitWithSet(applicablePrmVOList, maxBnfSet);
        }
    }

    private void setupMaxBenefitWithSet(List<ApplicablePrmVO> applicablePrmVOList, Set<Long> maxBnfSet){
        ApplicablePrmVO maxBnfPrm = getMaxBenfitPrm(applicablePrmVOList, maxBnfSet);
        maxBnfSet.add(maxBnfPrm.getCpnIssNo());
        maxBnfPrm.setMaxBenefitYn("Y");
    }

    @Override
    public PrmTypeCode getType() {
        return PrmTypeCode.PROUDCT_COUPON;
    }

}
