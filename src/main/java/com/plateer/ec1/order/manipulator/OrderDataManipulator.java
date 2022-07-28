package com.plateer.ec1.order.manipulator;

import com.plateer.ec1.order.mapper.OrdTrxMapper;
import com.plateer.ec1.order.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderDataManipulator {

    private final OrdTrxMapper ordTrxMapper;

    @Transactional
    public void insertOrder(OrderVO orderVO){

    }
}
