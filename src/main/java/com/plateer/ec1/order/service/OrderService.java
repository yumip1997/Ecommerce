package com.plateer.ec1.order.service;

import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.OrderRequestVO;
import com.plateer.ec1.order.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class OrderService {

    private final OrderContext orderContext;

    public void order(@Valid OrderRequestVO orderRequestVO){
        try{
            OrdClmCreationVO<OrderVO, Object> creationVO = orderContext.doOrderProcess(orderRequestVO);
            orderContext.doOrderAfterProcess(orderRequestVO, creationVO.getInsertData());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }


}
