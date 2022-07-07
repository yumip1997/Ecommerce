package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PrmResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import com.plateer.ec1.promotion.enums.PrmTypeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PriceDiscountCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<PrmAplyVO> prmAplyVOList = prmApplyMapper.getApplicablePdCupList(prmRequestBaseVO);
        calculate(prmAplyVOList);

        List<ProductInfoVO> productInfoVOList = prmAplyVOList.stream()
                .map(PrmAplyVO::getProductInfoVO)
                .collect(Collectors.toList());

        return PrmResponseVO.<ProductInfoVO>builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .list(productInfoVOList)
                .build();
    }


    private void calculate(List<PrmAplyVO> prmAplyVOList) {
        for (PrmAplyVO prmAplyVO : prmAplyVOList) {
            List<ApplicablePrmVO> applicablePrmVOList = prmAplyVO.getApplicablePrmVOList();
            if (CollectionUtils.isEmpty(applicablePrmVOList)) continue;

            setBnfVal(applicablePrmVOList, prmAplyVO.getProductInfoVO().getSalePrc());
            setUpPrmPrc(prmAplyVO);
        }
    }

    private void setUpPrmPrc(PrmAplyVO prmAplyVO){
        Optional<ApplicablePrmVO> maxBnfPrmOpt = getMaxBenefitPrm(prmAplyVO.getApplicablePrmVOList());

        maxBnfPrmOpt.ifPresent(maxBnfPrm -> {
            ProductInfoVO productInfoVO = prmAplyVO.getProductInfoVO();
            productInfoVO.setPrmPrc(productInfoVO.getSalePrc() - maxBnfPrm.getBnfVal());
        });
    }

    @Override
    public PrmTypeCode getType() {
        return PrmTypeCode.PRICE_DISCOUNT;
    }


}
