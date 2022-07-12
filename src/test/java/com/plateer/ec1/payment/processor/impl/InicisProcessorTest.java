package com.plateer.ec1.payment.processor.impl;

import com.google.gson.Gson;
import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InicisProcessorTest {

    @Autowired
    private InicisProcessor inicisProcessor;

    @Test
    void approve_test(){
        //TODO JSON 호출 방식으로 변경 예정
        OrderInfoVO orderInfoVO = OrderInfoVO.builder()
                .ordNo("020220712001")
                .goodName("상품1")
                .buyerEmail("youmi321@naver.com")
                .buyerName("박유미")
                .build();
        PayInfoVO payInfoVO = PayInfoVO.builder()
                .bankCode("03")
                .payAmount(10000)
                .depositorName("박유미")
                .paymentType(PaymentType.INICIS)
                .paymentBusiness(PaymentBusiness.VACCT_APV)
                .build();

        ApproveResVO approveResVO = inicisProcessor.approvePay(orderInfoVO, payInfoVO);
        Assertions.assertThat(approveResVO.getPaymentType()).isEqualTo(PaymentType.INICIS);
    }
}