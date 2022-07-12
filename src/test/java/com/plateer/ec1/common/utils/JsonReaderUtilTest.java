package com.plateer.ec1.common.utils;

import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class JsonReaderUtilTest {

    private static final String FILE_PATH = "./src/test/java/com/plateer/ec1/resource/";

    @Test
    void readTest(){
        JsonReaderUtil jsonReaderUtil = new JsonReaderUtil(FILE_PATH);

        OrderInfoVO orderInfoVO = jsonReaderUtil.getObject("payment/OrderInfo.json", OrderInfoVO.class);
        log.info("orderInfoVO : {} ", orderInfoVO);

        PayInfoVO payInfoVO = jsonReaderUtil.getObject("payment/PayInfo.json", PayInfoVO.class);
        log.info("payInfoVO : {} ", payInfoVO);
    }
}