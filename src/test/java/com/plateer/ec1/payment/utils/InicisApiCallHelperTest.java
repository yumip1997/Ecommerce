package com.plateer.ec1.payment.utils;

import com.plateer.ec1.payment.vo.req.VirtualAccountReqVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InicisApiCallHelperTest {

    @Value("${payment.inicis.mid}")
    private String mid;

    @Value("${payment.inicis.api-key}")
    private String api_key;

    @Autowired
    private InicisApiCallHelper inicisApiCallHelper;

    @Test
    void test(){
        VirtualAccountReqVO virtualAccountReqVO =
                VirtualAccountReqVO.builder()
                        .mid(mid)
                        .url("aa")
                        .moid("ORDER123")
                        .goodName("상품1")
                        .buyerEmail("youmi321@naver.com")
                        .buyerName("박유미")
                        .currency(InicisConstants.CURRENCY_WON)
                        .bankCode("10")
                        .nmInput("박유미")
                        .build();

        virtualAccountReqVO.setUpVirtualAccountReqVO(api_key);

        inicisApiCallHelper.callVirtualAccountNum(virtualAccountReqVO);
    }
}