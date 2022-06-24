package com.plateer.ec1.order.service;

import com.plateer.ec1.order.vo.OrderRequestVO;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.PayInfoVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    @DisplayName("FO 일반상품 주문")
    void test_fo_grl() throws Exception {
        OrderRequestVO orderRequestVO = OrderRequestVO.builder()
                .orderType("GL")
                .systemType("FO")
                .payInfoVO(PayInfoVO.builder().paymentType(PaymentType.INICIS).build())
                .build();

        orderService.order(orderRequestVO);
    }

    @Test
    @DisplayName("BO 일반상품 주문")
    void test_bo_grl() throws Exception {
        OrderRequestVO orderRequestVO = OrderRequestVO.builder()
                .orderType("GL")
                .systemType("BO")
                .payInfoVO(PayInfoVO.builder().paymentType(PaymentType.INICIS).build())
                .build();

        orderService.order(orderRequestVO);
    }

    @Test
    @DisplayName("FO 이쿠폰 주문")
    void test_fo_EC() throws Exception {
        OrderRequestVO orderRequestVO = OrderRequestVO.builder()
                .orderType("EC")
                .systemType("FO")
                .payInfoVO(PayInfoVO.builder().paymentType(PaymentType.INICIS).build())
                .build();

        orderService.order(orderRequestVO);
    }

    @Test
    @DisplayName("FO 이쿠폰 주문")
    void test_bo_EC() throws Exception {
        OrderRequestVO orderRequestVO = OrderRequestVO.builder()
                .orderType("EC")
                .systemType("BO")
                .payInfoVO(PayInfoVO.builder().paymentType(PaymentType.INICIS).build())
                .build();

        orderService.order(orderRequestVO);
    }
}