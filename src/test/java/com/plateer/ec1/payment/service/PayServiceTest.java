package com.plateer.ec1.payment.service;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.common.utils.LocalDateTimeUtil;
import com.plateer.ec1.order.service.OrderService;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.req.ApproveReqVO;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
class PayServiceTest {

    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "payment");
    }

    @Test
    @DisplayName("승인 요청 req valiation test - 주문번호가 없을 경우")
    void null_ordno(){
        OrderInfoVO orderInfoVO = jsonReaderUtil.getObject("/OrderInfoNullOrdNo.json", OrderInfoVO.class);
        PayInfoVO payInfoVO = jsonReaderUtil.getObject("/PayInfo.json", PayInfoVO.class);

        ApproveReqVO reqVO = ApproveReqVO.builder().orderInfoVO(orderInfoVO).payInfoVOList(Arrays.asList(payInfoVO)).build();
        Assertions.assertThrows(ConstraintViolationException.class, ()-> payService.approve(reqVO));
    }

    @Test
    @DisplayName("승인 요청 req valiation test - 결제정보 없을 경우")
    void empty_payinfo(){
        OrderInfoVO orderInfoVO = jsonReaderUtil.getObject("/OrderInfo.json", OrderInfoVO.class);

        ApproveReqVO reqVO = ApproveReqVO.builder().orderInfoVO(orderInfoVO).payInfoVOList(new ArrayList<>()).build();
        Assertions.assertThrows(ConstraintViolationException.class, ()-> payService.approve(reqVO));
    }

    @Test
    @DisplayName("승인 요청 req valiation test - 결제금액 없을 경우")
    void null_payAmount(){
        OrderInfoVO orderInfoVO = jsonReaderUtil.getObject("/OrderInfo.json", OrderInfoVO.class);
        PayInfoVO payInfoVO = jsonReaderUtil.getObject("/PayInfo.json", PayInfoVO.class);
        payInfoVO.setPayAmount(null);

        ApproveReqVO reqVO = ApproveReqVO.builder().orderInfoVO(orderInfoVO).payInfoVOList(Arrays.asList(payInfoVO)).build();
        Assertions.assertThrows(ConstraintViolationException.class, ()-> payService.approve(reqVO));
    }

    @Test
    @DisplayName("승인 요청 req valiation test - paymentType이 없을 경우 없을 경우")
    void null_bankCode(){
        OrderInfoVO orderInfoVO = jsonReaderUtil.getObject("/OrderInfo.json", OrderInfoVO.class);
        PayInfoVO payInfoVO = jsonReaderUtil.getObject("/PayInfo.json", PayInfoVO.class);
        payInfoVO.setPaymentType(null);

        ApproveReqVO reqVO = ApproveReqVO.builder().orderInfoVO(orderInfoVO).payInfoVOList(Arrays.asList(payInfoVO)).build();
        Assertions.assertThrows(ConstraintViolationException.class, ()-> payService.approve(reqVO));
    }

    @Test
    @DisplayName("입금 전 부분취소 테스트")
    void partial_cancel_before_deposit(){
        JsonReaderUtil orderJsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "order");
        OrderRequestVO orderReq = orderJsonReaderUtil.getObject("/OrderRequest.json", OrderRequestVO.class);
        String ordNo = "O"+ LocalDateTimeUtil.toStringYearToSeconds(LocalDateTime.now());
        orderReq.setOrdNo(ordNo);
        orderService.order(orderReq);

        PaymentCancelReqVO object = jsonReaderUtil.getObject("/CancelPartialBefore.json", PaymentCancelReqVO.class);
        object.setOrdNo(ordNo);
        payService.cancel(object);
    }

    @Test
    @DisplayName("입금 전 전체취소 테스트")
    void all_cancel_before_deposit(){
        JsonReaderUtil orderJsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "order");
        OrderRequestVO orderReq = orderJsonReaderUtil.getObject("/OrderRequest.json", OrderRequestVO.class);
        String ordNo = "O"+ LocalDateTimeUtil.toStringYearToSeconds(LocalDateTime.now());
        orderReq.setOrdNo(ordNo);
        orderService.order(orderReq);

        PaymentCancelReqVO object = jsonReaderUtil.getObject("/CancelAllBefore.json", PaymentCancelReqVO.class);
        object.setOrdNo(ordNo);
        payService.cancel(object);
    }


}