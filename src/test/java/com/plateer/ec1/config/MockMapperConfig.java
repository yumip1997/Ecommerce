package com.plateer.ec1.config;

import com.plateer.ec1.dao.payment.PaymentMockMapper;
import com.plateer.ec1.payment.mapper.PaymentMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MockMapperConfig {

    @Bean
    @Primary
    public PaymentMapper paymentMapper(){
        return new PaymentMockMapper();
    }
}
