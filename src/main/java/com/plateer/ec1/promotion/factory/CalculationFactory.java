package com.plateer.ec1.promotion.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.promotion.enums.PromotionType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CalculationFactory extends FactoryTemplate<PromotionType, Calculation> {

    public CalculationFactory(List<Calculation> list) {
        super(list);
    }
}
