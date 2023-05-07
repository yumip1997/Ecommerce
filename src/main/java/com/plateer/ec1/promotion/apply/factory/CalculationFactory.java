package com.plateer.ec1.promotion.apply.factory;

import com.plateer.ec1.common.factory.AbstractFactory;
import com.plateer.ec1.promotion.apply.calculator.Calculator;
import com.plateer.ec1.promotion.enums.PrmTypeCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CalculationFactory extends AbstractFactory<PrmTypeCode, Calculator> {

    public CalculationFactory(List<Calculator> list) {
        super(list);
    }
}
