package com.plateer.ec1.promotion.download.service;

import com.plateer.ec1.promotion.vo.request.CupDwlRequestVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DownloadServiceTest {

    @Autowired
    private DownloadService downloadService;


    @Test
    @DisplayName("다운로드 흐름")
    void test(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().dwlCupType("GC").build();
        downloadService.download(cupDwlRequestVO);
    }
}