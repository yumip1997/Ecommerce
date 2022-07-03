package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.mapper.PrmApplyMapper;
import com.plateer.ec1.promotion.apply.vo.PdPrmVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.ApplicablePrmVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.PrmResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import com.plateer.ec1.promotion.enums.PrmTypeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Component
public class ProductCouponCalculator implements Calculator {

    private final PrmApplyMapper prmApplyMapper;

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        List<PdPrmVO> pdPrmVOList = prmApplyMapper.getApplicablePrmList(prmRequestBaseVO);
        List<PrmAplyVO> prmAplyVOList = groupByProductInfo(pdPrmVOList);
        calculate(prmAplyVOList);

        return PrmResponseVO.<PrmAplyVO>builder()
                .mbrNo(prmRequestBaseVO.getMbrNo())
                .list(prmAplyVOList)
                .build();
    }

    //상품단품 번호로 그룹핑한다 (상품1 - 프로모션N)
    public List<PrmAplyVO> groupByProductInfo(List<PdPrmVO> pdPrmVOList){
        Map<String, List<PdPrmVO>> collect = pdPrmVOList.stream().collect(groupingBy(PdPrmVO::getGoodsItemNo));
        return collect.entrySet().stream()
                .map(this::convertPrmAplyVO)
                .collect(Collectors.toList());
    }

    private PrmAplyVO convertPrmAplyVO(Map.Entry<String, List<PdPrmVO>> entry){
        ProductInfoVO productInfoVO = entry.getValue().stream().map(PdPrmVO::getProductInfoVO).findFirst().orElse(ProductInfoVO.builder().build());
        List<ApplicablePrmVO> applicablePrmVOList = entry.getValue().stream().map(PdPrmVO::getApplicablePrmVO).collect(Collectors.toList());
        return PrmAplyVO.builder().productInfoVO(productInfoVO).applicablePrmVOList(applicablePrmVOList).build();
    }

    public void calculate(List<PrmAplyVO> prmAplyVOList){
        Set<Long> maxBnfSet = new HashSet<>();

        for (PrmAplyVO prmAplyVO : prmAplyVOList) {
            List<ApplicablePrmVO> applicablePrmVOList = prmAplyVO.getApplicablePrmVOList();
            if(CollectionUtils.isEmpty(applicablePrmVOList)) continue;

            setBnfVal(applicablePrmVOList, prmAplyVO.getProductInfoVO().getOrrAt());

            ApplicablePrmVO maxBnfPrm = getMaxBenfitPrm(applicablePrmVOList, maxBnfSet);
            maxBnfSet.add(maxBnfPrm.getCpnIssNo());
            maxBnfPrm.setMaxBenefitYn("Y");
        }

    }

    @Override
    public PrmTypeCode getType() {
        return PrmTypeCode.PROUDCT_COUPON;
    }

}
