package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.mapper.OrderMapper;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OrderValidatorTest {

    @Autowired
    private OrderMapper orderMapper;
    private JsonReaderUtil jsonReaderUtil;
    private OrderRequestVO requestVO;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "order");
        requestVO = jsonReaderUtil.getObject("/OrderRequest.json", OrderRequestVO.class);
    }

    public OrderValidator getValidator(OrderBasicVO orderBasicVO){
        String ordSysCcd = orderBasicVO.getOrdSysCcd();
        String ordTpCd = orderBasicVO.getOrdTpCd();
        return OrderValidator.findOrderValidator(ordSysCcd, ordTpCd);
    }

    @Test
    void fo_general_test(){
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(requestVO.getOrderProductVOList());
        OrderValidator orderValidator = getValidator(requestVO.getOrderBasicVO());
        orderValidator.isValid(requestVO, orderProductView);
    }

    @Test
    @DisplayName("주문 상품이 존재하지 않을 경우 예외가 발생한다.")
    void fo_general_not_exsit_prd_test(){
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(requestVO.getOrderProductVOList());
        orderProductView.get(0).setProductInfoVO(null);
        OrderValidator orderValidator = getValidator(requestVO.getOrderBasicVO());
        Assertions.assertThrows(ValidationException.class, () -> orderValidator.isValid(requestVO, orderProductView));
    }

    @Test
    @DisplayName("주문한 상품이 판매중이지 않을 경우 예외가 발생한다.")
    void fo_general_not_selling_prd_test(){
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(requestVO.getOrderProductVOList());
        orderProductView.get(0).getProductInfoVO().setPrgsStatCd("30");

        OrderValidator orderValidator = getValidator(requestVO.getOrderBasicVO());
        Assertions.assertThrows(ValidationException.class, () -> orderValidator.isValid(requestVO, orderProductView));
    }

    @Test
    @DisplayName("일반 주문을 했는데 일반 상품이 아닐 경우 예외가 발생한다.")
    void fo_general_not_general_prd_test(){
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(requestVO.getOrderProductVOList());
        orderProductView.get(0).getProductInfoVO().setGoodsTpCd("20");

        OrderValidator orderValidator = getValidator(requestVO.getOrderBasicVO());
        Assertions.assertThrows(ValidationException.class, () -> orderValidator.isValid(requestVO, orderProductView));
    }

    @Test
    @DisplayName("일반 주문을 했는데 수취인 이름이 없을 경우 예외가 발생한다.")
    void fo_general_not_rmt_nm_test(){
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(requestVO.getOrderProductVOList());
        requestVO.getOrderDeliveryVOList().get(0).setRmtiNm("");

        OrderValidator orderValidator = getValidator(requestVO.getOrderBasicVO());
        Assertions.assertThrows(ValidationException.class, () -> orderValidator.isValid(requestVO, orderProductView));
    }

    @Test
    @DisplayName("일반 주문을 했는데 수취인 휴대폰 번호이 없을 경우 예외가 발생한다.")
    void fo_general_not_rmt_hp_test(){
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(requestVO.getOrderProductVOList());
        requestVO.getOrderDeliveryVOList().get(0).setRmtiHpNo("");

        OrderValidator orderValidator = getValidator(requestVO.getOrderBasicVO());
        Assertions.assertThrows(ValidationException.class, () -> orderValidator.isValid(requestVO, orderProductView));
    }

    @Test
    @DisplayName("일반 주문을 했는데 수취인 주소가 없을 경우 예외가 발생한다.")
    void fo_general_not_rmt_ad_test(){
        List<OrderProductView> orderProductView = orderMapper.getOrderProductView(requestVO.getOrderProductVOList());
        requestVO.getOrderDeliveryVOList().get(0).setRmtiAddr("");

        OrderValidator orderValidator = getValidator(requestVO.getOrderBasicVO());
        Assertions.assertThrows(ValidationException.class, () -> orderValidator.isValid(requestVO, orderProductView));
    }
}