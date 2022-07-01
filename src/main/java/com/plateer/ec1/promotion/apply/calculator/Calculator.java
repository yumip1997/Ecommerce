package com.plateer.ec1.promotion.apply.calculator;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.enums.PRM0003Code;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;

import java.util.List;

public interface Calculator extends CustomFactory<PRM0004Code> {

    ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO);

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
