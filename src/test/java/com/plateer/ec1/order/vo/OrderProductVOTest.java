package com.plateer.ec1.order.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductVOTest {

    @Test
    void test(){
        OrderProductVO  orderProductVO = new OrderProductVO();
        long prdBnfAplyOrdPrc = orderProductVO.getPrdBnfAplyOrdPrc();
        Assertions.assertThat(prdBnfAplyOrdPrc).isEqualTo(0);
    }

}