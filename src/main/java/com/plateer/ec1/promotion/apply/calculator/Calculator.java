package com.plateer.ec1.promotion.apply.calculator;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.product.Product;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.enums.PRM0003Code;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import com.plateer.ec1.promotion.apply.vo.ApplicableCupVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;

import java.util.Comparator;
import java.util.List;

public interface Calculator extends CustomFactory<PRM0004Code> {

    ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO);

    void calculate(List<PrmAplyVO> prmAplyVOList);

    default ApplicableCupVO getMaxBenefitPrm(List<ApplicableCupVO> applicableCupVOList){
        return applicableCupVOList.stream()
                .max(Comparator.comparingLong(ApplicableCupVO::getBnfVal))
                .orElse(ApplicableCupVO.builder().build());
    }

    default void setBnfVal(PrmAplyVO prmAplyVO){
        for (ApplicableCupVO applicableCupVO : prmAplyVO.getApplicableCupVOList()) {
            Product product = prmAplyVO.getProduct();
            Long productPrice =  Long.min(product.getSalePrc(), product.getPrmPrc());

            Long bnfVal = getBnfVal(productPrice, applicableCupVO);
            applicableCupVO.setBnfVal(bnfVal);
        }
    }

    default Long getBnfVal(Long productPrice, ApplicableCupVO applicableCupVO){
        Long bnfVal = PRM0003Code.getBnfValFunction(applicableCupVO.getDcCcd())
                .apply(productPrice, applicableCupVO.getDcVal());

        return Long.min(bnfVal, applicableCupVO.getMaxDcAmt());
    }


}
