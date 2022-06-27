package com.plateer.ec1.promotion.download.downloader.impl;

import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeneralCupDownloaderTest {

    @Autowired
    private GeneralCupDownloader generalCupDownloader;

    @Test
    @DisplayName("쿠폰 다운 완료")
    void dwl() throws Exception {
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().prmNo(1L).mbrNo("user1").dwlCupType("GC").build();

        generalCupDownloader.download(cupDwlRequestVO);
    }
}