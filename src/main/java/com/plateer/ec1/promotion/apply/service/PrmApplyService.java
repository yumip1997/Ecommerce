package com.plateer.ec1.promotion.apply.service;

import com.plateer.ec1.promotion.enums.PRM0004Code;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.apply.factory.CalculationFactory;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrmApplyService {

    private final CalculationFactory calculationFactory;

    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO){
        Calculator calculator = calculationFactory.get(PRM0004Code.findPromotionType(prmRequestBaseVO.getPrmTypeCode()));
        return calculator.getCalculationData(prmRequestBaseVO);
    }

}
