package com.plateer.ec1.order.service;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.excpetion.custom.DataCreationException;
import com.plateer.ec1.common.excpetion.custom.PaymentException;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.common.utils.LocalDateTimeUtil;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderProductVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
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

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    private JsonReaderUtil jsonReaderUtil;
    private OrderRequestVO requestVO;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "order");
        requestVO = jsonReaderUtil.getObject("/OrderRequest.json", OrderRequestVO.class);
        requestVO.setOrdNo("O"+ LocalDateTimeUtil.toStringYearToSeconds(LocalDateTime.now()));

    }

    @Test
    void mbr_no_null_test(){
        requestVO.getOrderBasicVO().setMbrNo(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> orderService.order(requestVO));
    }

    @Test
    void product_list_empty_test(){
        requestVO.setOrderProductVOList(new ArrayList<>());
        Assertions.assertThrows(ConstraintViolationException.class, () -> orderService.order(requestVO));
    }

    @Test
    void order_test(){
        orderService.order(requestVO);
    }

    @Test
    void order_with_point_test(){
        OrderRequestVO requestVO = jsonReaderUtil.getObject("/OrderRequestWithPoint.json", OrderRequestVO.class);
        requestVO.setOrdNo("O"+ LocalDateTimeUtil.toStringYearToSeconds(LocalDateTime.now()));
        orderService.order(requestVO);
    }

    @Test
    void order_with_cup_test(){
        OrderRequestVO requestVO = jsonReaderUtil.getObject("/OrderRequestWithPrdCartCup.json", OrderRequestVO.class);
        requestVO.setOrdNo("O"+ LocalDateTimeUtil.toStringYearToSeconds(LocalDateTime.now()));
        orderService.order(requestVO);
    }

    @Test
    @DisplayName("데이터 유효성 검증 실패시 ValidationException이 발생한다.")
    void fail_validation(){
        OrderBasicVO orderBasicVO = requestVO.getOrderBasicVO();
        orderBasicVO.setOrdTpCd("20");

        Assertions.assertThrows(ValidationException.class, () -> orderService.order(requestVO));
    }

    @Test
    @DisplayName("이니시스 요청 실패시 PaymentException이 발생한다.")
    void fail_payment(){
        PayInfoVO payInfoVO = requestVO.getPayInfoVOList().get(0);
        payInfoVO.setPayAmount(0L);

        Assertions.assertThrows(PaymentException.class, () -> orderService.order(requestVO));
    }

    @Test
    @DisplayName("금액 검증 실패 시 BusinessException이 발생한다.")
    void fail_validate_amount(){
        PayInfoVO payInfoVO = requestVO.getPayInfoVOList().get(0);
        payInfoVO.setPayAmount(99L);

        Assertions.assertThrows(BusinessException.class, () -> orderService.order(requestVO));
    }

}