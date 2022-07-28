package com.plateer.ec1.order.service;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderContextTest {

    @Autowired
    private OrderContext orderContext;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "order");
    }

    @Test
    void insert_test(){
        OrderRequestVO object = jsonReaderUtil.getObject("/OrderRequest.json", OrderRequestVO.class);
    }
}