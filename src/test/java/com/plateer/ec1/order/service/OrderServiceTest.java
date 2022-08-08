package com.plateer.ec1.order.service;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.common.utils.LocalDateTimeUtil;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
        requestVO = jsonReaderUtil.getObject("/OrderRequestWithPrdCartCup.json", OrderRequestVO.class);
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

}