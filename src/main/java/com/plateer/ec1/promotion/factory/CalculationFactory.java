package com.plateer.ec1.promotion.factory;

import com.plateer.ec1.promotion.enums.PromotionType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CalculationFactory {

    private final Map<PromotionType, Calculation> map = new HashMap<>();

    public CalculationFactory(List<Calculation> calculation){
        calculation.forEach(c -> map.put(c.getType(), c));
    }

    public Calculation getCalculation(PromotionType prmTypeCode){
        return map.get(prmTypeCode);
    }
}
