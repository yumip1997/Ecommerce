package com.plateer.ec1.order.service;

import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.mapper.OrderDao;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.OrderProductViewVO;
import com.plateer.ec1.order.vo.OrderRequestVO;
import com.plateer.ec1.order.vo.OrderValidationVO;
import com.plateer.ec1.payment.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Log4j2
public class OrderContext {

    private final OrderHistoryService orderHistoryService;
    private final OrderDao orderDao;
    private final PayService payService;

    public void execute(DataStrategy dataStrategy, AfterStrategy afterStrategy, OrderRequestVO orderRequestVO){
        //TODO OrderDao에서 가져오기
        OrderProductViewVO productView = new OrderProductViewVO();

        Long logKey = null;

        try{
            //주문 모니터링 로그 생성
            logKey = orderHistoryService.insertOrderHistory(orderRequestVO);

            //파라미터 유혀성 검증
            validate(orderRequestVO);

            //주문데이터생성
            OrderVO orderVO = dataStrategy.create(orderRequestVO, productView);

            //결제호출
            payService.approve(orderRequestVO.getPayInfoVO());

            //주문데이터등록
            insertOrderData(orderVO);

            //TODO 금액검증

            afterStrategy.call(orderRequestVO, orderVO);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    private void validate(OrderRequestVO orderRequestVO) throws Exception {
        log.info("파라미터 유효성 체크를 한다.");
        OrderValidator orderValidator = OrderValidator.findOrderValidatory(orderRequestVO);
        boolean isValid = orderValidator.test(new OrderValidationVO());

        if(isValid) return;
        throw new Exception(OrderException.INVALID_ORDER.msg);
    }

    private void insertOrderData(OrderVO orderVO){
        log.info("주문 데이터 insert 로직이 진행된다.");
        //TODO orderDao.insertOrderData(orderDto);
    }

}
