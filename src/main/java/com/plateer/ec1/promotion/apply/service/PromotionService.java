package com.plateer.ec1.promotion.apply.service;

import com.plateer.ec1.promotion.enums.PRM0004Code;
import com.plateer.ec1.promotion.apply.calculator.Calculation;
import com.plateer.ec1.promotion.apply.factory.CalculationFactory;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final CalculationFactory calculationFactory;

    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO){
        Calculation calculation = calculationFactory.get(PRM0004Code.findPromotionType(prmRequestBaseVO.getPrmTypeCode()));
        return calculation.getCalculationData(prmRequestBaseVO);
    }

}
