package com.plateer.ec1.order.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderProductVOTest {

    @Test
    @DisplayName("적용 상품 쿠폰이 없을 경우 상품쿠폰 혜택 금액은 총 0이다.")
    void empty_prd_bnf_test(){
        OrderProductVO  orderProductVO = new OrderProductVO();
        long sumPrdBnf = orderProductVO.sumPrdBnf();
        Assertions.assertThat(sumPrdBnf).isEqualTo(0);
    }
}