package com.plateer.ec1.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.resource.TestConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
class JsonReaderUtilTest {

    @Test
    void readTest(){
        JsonReaderUtil jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH);

        OrderInfoVO orderInfoVO = jsonReaderUtil.getObject("payment/OrderInfo.json", OrderInfoVO.class);
        log.info("orderInfoVO : {} ", orderInfoVO);

        PayInfoVO payInfoVO = jsonReaderUtil.getObject("payment/PayInfo.json", PayInfoVO.class);
        log.info("payInfoVO : {} ", payInfoVO);


    }
}