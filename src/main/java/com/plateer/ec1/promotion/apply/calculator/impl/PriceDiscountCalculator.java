package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.common.model.product.Product;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.vo.ApplicablePdCupVO;
import com.plateer.ec1.promotion.apply.vo.PrmApplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PriceDiscountResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Log4j2
public class PriceDiscountCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<ApplicablePdCupVO> applicablePdCupList = prmApplyMapper.getApplicablePdCupList(prmRequestBaseVO);

        if(!CollectionUtils.isEmpty(applicablePdCupList)){
            calculate(applicablePdCupList);
        }

        List<Product> productList = applicablePdCupList.stream()
                .map(ApplicablePdCupVO::getProduct)
                .collect(Collectors.toList());

        return PriceDiscountResponseVO.builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .productList(productList)
                .build();
    }

    private void calculate(List<ApplicablePdCupVO> applicablePdCupVOList){
        for (ApplicablePdCupVO applicablePdCupVO : applicablePdCupVOList) {
            //혜택금액 셋팅
            setBnfVal(applicablePdCupVO);

            //최대혜택 셋팅 후 최대혜택 가격으로 가격조정 적용
            PrmApplyVO maxBnfPrm = getMaxBenefitPrm(applicablePdCupVO.getPrmApplyVOList());
            Product product = applicablePdCupVO.getProduct();

            product.setPrmPrc(product.getSalePrc() - maxBnfPrm.getBnfVal());
        }

    }

    @Override
    public PRM0004Code getType() {
        return PRM0004Code.PRICE_DISCOUNT;
    }



}
