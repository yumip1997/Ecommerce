package com.plateer.ec1.order.mapper;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.resource.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;
    private JsonReaderUtil jsonReaderUtil;
    private OrderRequestVO orderRequestVO;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "order");
        orderRequestVO = jsonReaderUtil.getObject("/OrderRequest.json", OrderRequestVO.class);
    }

    @Test
    void test(){
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(orderRequestVO.getOrderProductVOList());
        Assertions.assertThat(orderProductView).isNotEmpty();
    }
}