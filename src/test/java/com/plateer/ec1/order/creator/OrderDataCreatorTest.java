package com.plateer.ec1.order.creator;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.mapper.OrderMapper;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.resource.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDataCreatorTest {

    @Autowired
    private OrderMapper orderMapper;
    private JsonReaderUtil jsonReaderUtil;
    private OrderRequestVO orderRequestVO;
    private OrderRequestVO orderRequestVOWithMultipleDvGrp;
    private OrderRequestVO orderRequestVOWithPrdCup;
    private OrderRequestVO orderRequestVOWithCartCup;
    private OrderRequestVO orderRequestVOWithPrdCartCup;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "order");
        orderRequestVO = jsonReaderUtil.getObject("/OrderRequest.json", OrderRequestVO.class);
        orderRequestVOWithMultipleDvGrp = jsonReaderUtil.getObject("/OrderRequestWithMultipleDvGrp.json", OrderRequestVO.class);
        orderRequestVOWithPrdCup = jsonReaderUtil.getObject("/OrderRequestWithPrdCup.json", OrderRequestVO.class);
        orderRequestVOWithCartCup = jsonReaderUtil.getObject("/OrderRequestWithCartCup.json", OrderRequestVO.class);
        orderRequestVOWithPrdCartCup = jsonReaderUtil.getObject("/OrderRequestWithPrdCartCup.json", OrderRequestVO.class);
    }

    public void rowSizeTest(OrderVO insertData, OrderRequestVO orderRequestVO){
        Assertions.assertThat(insertData.getOpOrdBase()).isNotNull();
        Assertions.assertThat(insertData.getOpGoodsInfoList().size()).isEqualTo(orderRequestVO.getOrderProductVOList().size());
        Assertions.assertThat(insertData.getOpClmInfoList().size()).isEqualTo(orderRequestVO.getOrderProductVOList().size());
        Assertions.assertThat(insertData.getOpDvpAreaInfoList().size()).isEqualTo(orderRequestVO.getOrderDeliveryVOList().size());
        Assertions.assertThat(insertData.getOpDvpInfoList().size()).isEqualTo(getExpectedDvpInfoRowSize(orderRequestVO));
        Assertions.assertThat(insertData.getOpOrdCostInfoList().size()).isEqualTo(getExpectedOrdCostInfoRowSize(orderRequestVO));
        Assertions.assertThat(insertData.getOpOrdBnfInfoList().size()).isEqualTo(getExpectedBnfInfoRowSize(orderRequestVO));
        Assertions.assertThat(insertData.getOpOrdBnfRelInfoList().size()).isEqualTo(getExpectedBnfRelInfoRowSize(orderRequestVO));
    }

    public long getExpectedDvpInfoRowSize(OrderRequestVO orderRequestVO){
        return orderRequestVO.getOrderDeliveryVOList().stream()
                .mapToLong(e -> e.getOrderDeliveryGroupInfoVOList().size())
                .sum();
    }

    public long getExpectedOrdCostInfoRowSize(OrderRequestVO orderRequestVO){
        return orderRequestVO.getOrderDeliveryVOList().stream()
                .flatMap(e -> e.getOrderDeliveryGroupInfoVOList().stream())
                .mapToLong(e -> e.getOrderCostInfoList().size())
                .sum();
    }

    public long getExpectedBnfRelInfoRowSize(OrderRequestVO orderRequestVO){
        long prdRowSize = Optional.ofNullable(orderRequestVO.getOrderProductVOList())
                .orElse(Collections.emptyList())
                .stream()
                .mapToLong(e -> e.getProductBenefits() == null ? 0 : e.getProductBenefits().size())
                .sum();

        long cartRowSize = Optional.ofNullable(orderRequestVO.getOrderBenefitVOList())
                .orElse(Collections.emptyList())
                .stream()
                .mapToLong(e -> e.getOrderProductBaseVOList().size())
                .sum();

        return prdRowSize + cartRowSize;
    }

    public long getExpectedBnfInfoRowSize(OrderRequestVO orderRequestVO){
        long prdRowSize = Optional.ofNullable(orderRequestVO.getOrderProductVOList())
                .orElse(Collections.emptyList())
                .stream()
                .mapToLong(e -> e.getProductBenefits() == null ? 0 : e.getProductBenefits().size())
                .sum();

        long cartRowSize = Optional.ofNullable(orderRequestVO.getOrderBenefitVOList())
                .orElse(Collections.emptyList())
                .size();

        return prdRowSize + cartRowSize;
    }

    @Test
    public void test(){
        OrderDataCreator orderDataCreator = new OrderDataCreator(orderMapper);
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(orderRequestVO.getOrderProductVOList());
        OrdClmCreationVO<OrderVO, Object> creationVO = orderDataCreator.create(orderRequestVO, orderProductView);
        OrderVO insertData = creationVO.getInsertData();
        rowSizeTest(insertData, orderRequestVO);
    }

    @Test
    public void test_multiple_dv_grp(){
        OrderDataCreator orderDataCreator = new OrderDataCreator(orderMapper);
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(orderRequestVOWithMultipleDvGrp.getOrderProductVOList());
        OrdClmCreationVO<OrderVO, Object> creationVO = orderDataCreator.create(orderRequestVOWithMultipleDvGrp, orderProductView);
        OrderVO insertData = creationVO.getInsertData();
        rowSizeTest(insertData, orderRequestVOWithMultipleDvGrp);
    }

    @Test
    public void test_prdCup(){
        OrderDataCreator orderDataCreator = new OrderDataCreator(orderMapper);
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(orderRequestVOWithPrdCup.getOrderProductVOList());
        OrdClmCreationVO<OrderVO, Object> creationVO = orderDataCreator.create(orderRequestVOWithPrdCup, orderProductView);
        OrderVO insertData = creationVO.getInsertData();
        rowSizeTest(insertData, orderRequestVOWithPrdCup);
    }

    @Test
    public void test_cartCup(){
        OrderDataCreator orderDataCreator = new OrderDataCreator(orderMapper);
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(orderRequestVOWithCartCup.getOrderProductVOList());
        OrdClmCreationVO<OrderVO, Object> creationVO = orderDataCreator.create(orderRequestVOWithCartCup, orderProductView);
        OrderVO insertData = creationVO.getInsertData();
        rowSizeTest(insertData, orderRequestVOWithCartCup);
    }

    @Test
    public void test_prdCartCup(){
        OrderDataCreator orderDataCreator = new OrderDataCreator(orderMapper);
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(orderRequestVOWithPrdCartCup.getOrderProductVOList());
        OrdClmCreationVO<OrderVO, Object> creationVO = orderDataCreator.create(orderRequestVOWithPrdCartCup, orderProductView);
        OrderVO insertData = creationVO.getInsertData();
        rowSizeTest(insertData, orderRequestVOWithPrdCartCup);
    }


}