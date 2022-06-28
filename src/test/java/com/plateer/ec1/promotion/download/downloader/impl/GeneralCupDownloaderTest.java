package com.plateer.ec1.promotion.download.downloader.impl;

import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import org.junit.jupiter.api.Assertions;
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
    @DisplayName("프로모션 번호가 없을 경우 예외가 발생한다.")
    void null_prm(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().mbrNo("m1").build();

        Assertions.assertThrows(RuntimeException.class, () -> generalCupDownloader.download(cupDwlRequestVO));
    }

    @Test
    @DisplayName("회원 번호가 없을 경우 예외가 발생한다.")
    void null_mbr(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().prmNo(1L).build();

        Assertions.assertThrows(RuntimeException.class, () -> generalCupDownloader.download(cupDwlRequestVO));
    }

    @Test
    @DisplayName("프로모션 번호를 조회할 수 없을 경우 예외가 발생한다.")
    void not_found_prm(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().prmNo(100L).mbrNo("m1").build();

        Assertions.assertThrows(RuntimeException.class, () -> generalCupDownloader.download(cupDwlRequestVO));
    }

    @Test
    @DisplayName("쿠폰 다운 완료")
    void dwl(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().prmNo(1L).mbrNo("user2").dwlCupType("10").build();

        generalCupDownloader.download(cupDwlRequestVO);
    }
}