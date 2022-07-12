package com.plateer.ec1.payment.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentProcessorFactory extends FactoryTemplate<PaymentType, PaymentProcessor> {

    public PaymentProcessorFactory(List<PaymentProcessor> list) {
        super(list);
    }
}
