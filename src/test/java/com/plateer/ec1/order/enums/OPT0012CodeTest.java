package com.plateer.ec1.order.enums;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.excpetion.custom.DataCreationException;
import com.plateer.ec1.common.excpetion.custom.PaymentException;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class OPT0012CodeTest {

    @Test
    void success_test(){
        Assertions.assertThat(OPT0012Code.getCodeByException(null)).isEqualTo(OPT0012Code.SC.getCode());
    }

    @Test
    void valid_exception_test(){
        Exception exception = new ValidationException("유효성 검사 오류");
        Assertions.assertThat(OPT0012Code.getCodeByException(exception)).isEqualTo(OPT0012Code.FV.getCode());
    }

    @Test
    void data_creation_exception_test(){
        Exception exception = new DataCreationException("데이터 생성 오류");
        Assertions.assertThat(OPT0012Code.getCodeByException(exception)).isEqualTo(OPT0012Code.FD.getCode());
    }

    @Test
    void payment_exception_test(){
        Exception exception = new PaymentException("결제 오류");
        Assertions.assertThat(OPT0012Code.getCodeByException(exception)).isEqualTo(OPT0012Code.FP.getCode());
    }

    @Test
    void business_exception_test(){
        Exception exception = new BusinessException("");
        Assertions.assertThat(OPT0012Code.getCodeByException(exception)).isEqualTo("");
    }

    @Test
    void exception_test(){
        Exception exception = new Exception("");
        Assertions.assertThat(OPT0012Code.getCodeByException(exception)).isEqualTo("");
    }

}