package com.plateer.ec1.promotion.apply.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.enums.PRM0004Code;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculationFactory extends FactoryTemplate<PRM0004Code, Calculator> {

    public CalculationFactory(List<Calculator> list) {
        super(list);
    }
}
