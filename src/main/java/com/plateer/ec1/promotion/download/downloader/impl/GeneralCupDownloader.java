package com.plateer.ec1.promotion.download.downloader.impl;

import com.plateer.ec1.common.aop.LoginIdSetting;
import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.download.mapper.CupDwlTrxMapper;
import com.plateer.ec1.promotion.download.validator.CupDownloadValidator;
import com.plateer.ec1.promotion.enums.DwlCupType;
import com.plateer.ec1.promotion.apply.vo.request.CupDwlRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class GeneralCupDownloader implements CupDownloader {

    private final CupDownloadValidator cupDownloadValidator;
    private final CupDwlTrxMapper cupDwlTrxMapper;

    @Override
    public DwlCupType getType() {
        return DwlCupType.General_Cup;
    }

    @Override
    @LoginIdSetting
    public void download(CupDwlRequestVO cupDwlRequestVO) {
        log.info("일반 쿠폰 다운로드");
        cupDownloadValidator.isValid(cupDwlRequestVO);
        cupDwlTrxMapper.download(cupDwlRequestVO);
    }
}
