package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.apply.vo.ApplicableCupVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PriceDiscountResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PriceDiscountCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<PrmAplyVO> applicablePdCupList = prmApplyMapper.getApplicablePrmList(prmRequestBaseVO);
        calculate(applicablePdCupList);

        List<ProductInfoVO> productInfoVOList = applicablePdCupList.stream()
                .map(PrmAplyVO::getProductInfoVO)
                .collect(Collectors.toList());

        return PriceDiscountResponseVO.builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .productInfoVOList(productInfoVOList)
                .build();
    }

    @Override
    public void calculate(List<PrmAplyVO> prmAplyVOList){
        for (PrmAplyVO prmAplyVO : prmAplyVOList) {
            List<ApplicableCupVO> applicableCupVOList = prmAplyVO.getApplicableCupVOList();
            if(CollectionUtils.isEmpty(applicableCupVOList)) continue;

            setBnfVal(prmAplyVO);

            ApplicableCupVO maxBnfPrm = getMaxBenefitPrm(applicableCupVOList);
            ProductInfoVO productInfoVO = prmAplyVO.getProductInfoVO();

            productInfoVO.setPrmPrc(productInfoVO.getSalePrc() - maxBnfPrm.getBnfVal());
        }

    }

    @Override
    public PRM0004Code getType() {
        return PRM0004Code.PRICE_DISCOUNT;
    }



}
