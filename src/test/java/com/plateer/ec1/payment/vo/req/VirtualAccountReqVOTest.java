package com.plateer.ec1.payment.vo.req;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VirtualAccountReqVOTest {

    @Test
    void test() throws UnknownHostException {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"));

        LocalDateTime after1day = now.plusDays(1);
        String dtInput = after1day.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String tmInput = after1day.format(DateTimeFormatter.ofPattern("hhmm"));

        String ip = InetAddress.getLocalHost().getHostAddress();
    }
}