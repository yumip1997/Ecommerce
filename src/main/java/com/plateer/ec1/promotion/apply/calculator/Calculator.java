package com.plateer.ec1.promotion.apply.calculator;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import com.plateer.ec1.promotion.enums.PRM0003Code;
import com.plateer.ec1.promotion.enums.PrmTypeCode;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Calculator extends CustomFactory<PrmTypeCode> {

    ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO);

    default void setBnfVal(List<ApplicablePrmVO> applicablePrmVOList, Long price){
        for (ApplicablePrmVO applicablePrmVO : applicablePrmVOList) {
            Long bnfVal = getBnfVal(applicablePrmVO, price);
            applicablePrmVO.setBnfVal(bnfVal);
        }
    }

    default Long getBnfVal(ApplicablePrmVO applicablePrmVO, Long productPrice){
        Long bnfVal = PRM0003Code.of(applicablePrmVO.getDcCcd())
                .getDiscountFunction()
                .apply(productPrice, applicablePrmVO.getDcVal());

        return Long.min(bnfVal, applicablePrmVO.getMaxDcAmt());
    }

    default Optional<ApplicablePrmVO> getMaxBenfitPrm(List<ApplicablePrmVO> applicablePrmVOList, Set<Long> maxBnfSet){
        return applicablePrmVOList.stream()
                .filter(e -> !maxBnfSet.contains(e.getCpnIssNo()))
                .max(ApplicablePrmVO::compareTo);
    }

    default Optional<ApplicablePrmVO> getMaxBenefitPrm(List<ApplicablePrmVO> applicablePrmVOList){
        return applicablePrmVOList.stream()
                .max(ApplicablePrmVO::compareTo);
    }

}
