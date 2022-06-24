package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.CancelReq;
import com.plateer.ec1.payment.vo.NetCancelReq;
import com.plateer.ec1.payment.vo.PayInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PayServiceTest {

    @Autowired
    private PayService payService;

    @Test
    @DisplayName("이니시스 승인")
    void test_inicis_approve(){
        PayInfo payInfo = PayInfo.builder().paymentType(PaymentType.INICIS).build();
        payService.approve(payInfo);
    }

    @Test
    @DisplayName("이니시스 결제취소")
    void test_inicis_canel(){
        CancelReq cancelReq = CancelReq.builder().paymentType(PaymentType.INICIS).build();
        payService.cancel(cancelReq);
    }

    @Test
    @DisplayName("이니시스 망취소")
    void test_inicis_netcancel(){
        NetCancelReq netCancelReq = NetCancelReq.builder().paymentType(PaymentType.INICIS).build();
        payService.netCancel(netCancelReq);
    }

    @Test
    @DisplayName("포인트 승인")
    void test_point_approve(){
        PayInfo payInfo = PayInfo.builder().paymentType(PaymentType.POINT).build();
        payService.approve(payInfo);
    }

    @Test
    @DisplayName("포인트 결제취소")
    void test_point_canel(){
        CancelReq cancelReq = CancelReq.builder().paymentType(PaymentType.POINT).build();
        payService.cancel(cancelReq);
    }

    @Test
    @DisplayName("포인트 망취소")
    void test_point_netcancel(){
        NetCancelReq netCancelReq = NetCancelReq.builder().paymentType(PaymentType.POINT).build();
        payService.netCancel(netCancelReq);
    }
}