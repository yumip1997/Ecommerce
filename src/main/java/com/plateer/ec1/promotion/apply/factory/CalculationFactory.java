package com.plateer.ec1.promotion.apply.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.promotion.apply.calculator.Calculation;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculationFactory extends FactoryTemplate<PRM0004Code, Calculation> {

    public CalculationFactory(List<Calculation> list) {
        super(list);
    }
}
