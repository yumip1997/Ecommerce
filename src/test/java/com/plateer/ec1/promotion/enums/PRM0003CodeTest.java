package com.plateer.ec1.promotion.enums;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PRM0003CodeTest {

    @Test
    void fixed_function_test(){
        Long discount = PRM0003Code.getDiscountFunction("10").apply(1000L, 100L);
        Assertions.assertThat(discount).isEqualTo(900L);
    }

    @Test
    void rate_function_test(){
        Long discount = PRM0003Code.getDiscountFunction("20").apply(1500L, 11L);
        Assertions.assertThat(discount).isEqualTo(1335L);
    }

}