package com.plateer.ec1.order.service;

import com.plateer.ec1.order.vo.OrderRequest;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.PayInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("FO 일반상품 주문")
    void test_fo_grl() throws Exception {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderType("GL")
                .systemType("FO")
                .payInfo(PayInfo.builder().paymentType(PaymentType.INICIS).build())
                .build();

        orderService.order(orderRequest);
    }

    @Test
    @DisplayName("BO 일반상품 주문")
    void test_bo_grl() throws Exception {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderType("GL")
                .systemType("BO")
                .payInfo(PayInfo.builder().paymentType(PaymentType.INICIS).build())
                .build();

        orderService.order(orderRequest);
    }

    @Test
    @DisplayName("FO 이쿠폰 주문")
    void test_fo_EC() throws Exception {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderType("EC")
                .systemType("FO")
                .payInfo(PayInfo.builder().paymentType(PaymentType.INICIS).build())
                .build();

        orderService.order(orderRequest);
    }

    @Test
    @DisplayName("FO 이쿠폰 주문")
    void test_bo_EC() throws Exception {
        OrderRequest orderRequest = OrderRequest.builder()
                .orderType("EC")
                .systemType("BO")
                .payInfo(PayInfo.builder().paymentType(PaymentType.INICIS).build())
                .build();

        orderService.order(orderRequest);
    }
}