package com.plateer.ec1.promotion.apply.calculator;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.product.Product;
import com.plateer.ec1.promotion.apply.vo.ApplicablePdCupVO;
import com.plateer.ec1.promotion.enums.PRM0003Code;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import com.plateer.ec1.promotion.apply.vo.PrmApplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;

import java.util.Comparator;
import java.util.List;

public interface Calculator extends CustomFactory<PRM0004Code> {

    ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO);

    default PrmApplyVO getMaxBenefitPrm(List<PrmApplyVO> prmApplyVOList){
        return prmApplyVOList.stream()
                .max(Comparator.comparingLong(PrmApplyVO::getBnfVal))
                .orElse(PrmApplyVO.builder().build());
    }

    default void setBnfVal(ApplicablePdCupVO applicablePdCupVO){
        for (PrmApplyVO prmApplyVO : applicablePdCupVO.getPrmApplyVOList()) {
            Product product = applicablePdCupVO.getProduct();
            Long productPrice =  Long.min(product.getSalePrc(), product.getPrmPrc());

            Long bnfVal = getBnfVal(productPrice, prmApplyVO);
            prmApplyVO.setBnfVal(bnfVal);
        }

    }

    default Long getBnfVal(Long productPrice, PrmApplyVO prmApplyVO){
        return PRM0003Code.getDiscountFunction(prmApplyVO.getDcCcd())
                .apply(productPrice, prmApplyVO.getDcVal());
    }


}
