package com.plateer.ec1.payment.utils;

import com.plateer.ec1.payment.vo.req.VacctSeqReqVO;
import com.plateer.ec1.payment.vo.res.VacctSeqResVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InicisServicePaymentServiceApiCallHelperTest {

    @Value("${payment.inicis.mid}")
    private String mid;

    @Value("${payment.inicis.api-key}")
    private String api_key;

    @Autowired
    private InicisApiCallHelper inicisApiCallHelper;

    @Test
    void test(){
        VacctSeqReqVO vacctSeqReqVO =
                VacctSeqReqVO.builder()
                        .url("aa")
                        .moid("ORDER123")
                        .goodName("상품1")
                        .buyerEmail("youmi321@naver.com")
                        .buyerName("박유미")
                        .currency(InicisConstants.CURRENCY_WON)
                        .price("10000")
                        .bankCode("03")
                        .nmInput("박유미")
                        .build();

        vacctSeqReqVO.setUpVirtualAccountReqVO(api_key, mid);

        VacctSeqResVO vacctSeqResVO = inicisApiCallHelper.callVacctSeq(vacctSeqReqVO);

        Assertions.assertThat(vacctSeqResVO.getResultCode()).isEqualTo("00");

    }
}