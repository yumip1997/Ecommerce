package com.plateer.ec1.promotion.service;

import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.factory.CalculationFactory;
import com.plateer.ec1.promotion.vo.request.PrmRequestBase;
import com.plateer.ec1.promotion.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final CalculationFactory calculationFactory;

    public ResponseBaseVO getCalculationData(PrmRequestBase prmRequestBase){
        Calculation calculation = calculationFactory.get(PromotionType.findPromotionType(prmRequestBase.getPrmTypeCode()));
        return calculation.getCalculationData(prmRequestBase);
    }

}
