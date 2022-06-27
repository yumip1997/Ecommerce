package com.plateer.ec1.promotion.download.service;

import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DownloadServiceTest {

    @Autowired
    private DownloadService downloadService;

    @Test
    @DisplayName("다운로드 흐름")
    void test(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().prmNo(1L).mbrNo("user2").dwlCupType("10").build();
        downloadService.download(cupDwlRequestVO);
    }
}