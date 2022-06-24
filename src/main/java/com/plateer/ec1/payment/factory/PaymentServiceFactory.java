package com.plateer.ec1.payment.factory;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentServiceFactory {

    private final Map<PaymentType, PaymentService> map = new HashMap<>();

    public PaymentServiceFactory(List<PaymentService> paymentServiceList){
        paymentServiceList.forEach(paymentService -> map.put(paymentService.getType(), paymentService));
    }

    public PaymentService getPaymentService(PaymentType paymentType){
        return map.get(paymentType);
    }
}
