package com.plateer.ec1.order.creator;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.mapper.OrderMapper;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDataCreatorTest {

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
    public void test(){
        OrderDataCreator orderDataCreator = new OrderDataCreator(orderMapper);
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(orderRequestVO.getOrderProductVOList());
        orderDataCreator.create(orderRequestVO, orderProductView);
    }


}