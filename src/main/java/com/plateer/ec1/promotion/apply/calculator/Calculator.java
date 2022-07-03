package com.plateer.ec1.promotion.apply.calculator;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.enums.PRM0003Code;
import com.plateer.ec1.promotion.enums.PrmTypeCode;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Calculator extends CustomFactory<PrmTypeCode> {

    ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO);

    default ApplicablePrmVO getMaxBenfitPrm(List<ApplicablePrmVO> applicablePrmVOList, Set<Long> maxBnfSet){
        return applicablePrmVOList.stream()
                .filter(e -> !maxBnfSet.contains(e.getCpnIssNo()))
                .max(ApplicablePrmVO::compareTo)
                .orElse(ApplicablePrmVO.builder().build());
    }

    default ApplicablePrmVO getMaxBenefitPrm(List<ApplicablePrmVO> applicablePrmVOList){
        return applicablePrmVOList.stream()
                .max(ApplicablePrmVO::compareTo)
                .orElse(ApplicablePrmVO.builder().build());
    }

    default void setBnfVal(List<ApplicablePrmVO> applicablePrmVOList, Long price){
        for (ApplicablePrmVO applicablePrmVO : applicablePrmVOList) {
            Long bnfVal = getBnfVal(price, applicablePrmVO);
            applicablePrmVO.setBnfVal(bnfVal);
        }
    }

    default Long getBnfVal(Long productPrice, ApplicablePrmVO applicablePrmVO){
        Long bnfVal = PRM0003Code.getBnfValFunction(applicablePrmVO.getDcCcd())
                .apply(productPrice, applicablePrmVO.getDcVal());

        return Long.min(bnfVal, applicablePrmVO.getMaxDcAmt());
    }


}
