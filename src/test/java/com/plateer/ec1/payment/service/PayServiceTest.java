package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.req.CancelReqVO;
import com.plateer.ec1.payment.vo.req.NetCancelReqVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PayServiceTest {

    @Autowired
    private PayService payService;

    @Test
    @DisplayName("이니시스 승인")
    void test_inicis_approve(){
        PayInfoVO payInfoVO = PayInfoVO.builder().paymentType(PaymentType.INICIS).build();
        payService.approve(payInfoVO);
    }

    @Test
    @DisplayName("이니시스 결제취소")
    void test_inicis_canel(){
        CancelReqVO cancelReqVO = CancelReqVO.builder().paymentType(PaymentType.INICIS).build();
        payService.cancel(cancelReqVO);
    }

    @Test
    @DisplayName("이니시스 망취소")
    void test_inicis_netcancel(){
        NetCancelReqVO netCancelReqVO = NetCancelReqVO.builder().paymentType(PaymentType.INICIS).build();
        payService.netCancel(netCancelReqVO);
    }

    @Test
    @DisplayName("포인트 승인")
    void test_point_approve(){
        PayInfoVO payInfoVO = PayInfoVO.builder().paymentType(PaymentType.POINT).build();
        payService.approve(payInfoVO);
    }

    @Test
    @DisplayName("포인트 결제취소")
    void test_point_canel(){
        CancelReqVO cancelReqVO = CancelReqVO.builder().paymentType(PaymentType.POINT).build();
        payService.cancel(cancelReqVO);
    }

    @Test
    @DisplayName("포인트 망취소")
    void test_point_netcancel(){
        NetCancelReqVO netCancelReqVO = NetCancelReqVO.builder().paymentType(PaymentType.POINT).build();
        payService.netCancel(netCancelReqVO);
    }
}