package com.plateer.ec1.promotion.download.downloader.impl;

import com.plateer.ec1.common.aop.LoginIdSetting;
import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.download.factory.CupDownloadValidatorFactory;
import com.plateer.ec1.promotion.download.mapper.CupDwlTrxMapper;
import com.plateer.ec1.promotion.download.validator.CupDownloadValidator;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Log4j2
public class GeneralCupDownloader implements CupDownloader {

    private final CupDownloadValidatorFactory cupDownloadValidatorFactory;
    private final CupDwlTrxMapper cupDwlTrxMapper;

    @Override
    public PRM0009Code getType() {
        return PRM0009Code.General_Cup;
    }

    @Override
    @LoginIdSetting
    @Transactional
    public void download(CupDwlRequestVO cupDwlRequestVO){
        log.info("일반 쿠폰 다운로드");
        CupDownloadValidator cupDownloadValidator = cupDownloadValidatorFactory.get(getType());
        cupDownloadValidator.isValid(cupDwlRequestVO);

        cupDwlTrxMapper.insertCup(cupDwlRequestVO);
    }

}
