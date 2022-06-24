package com.plateer.ec1.payment.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentServiceFactory extends FactoryTemplate<PaymentType, PaymentService> {

    public PaymentServiceFactory(List<PaymentService> list) {
        super(list);
    }
}
