package com.plateer.ec1.promotion.download.validator.impl;

import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.validator.CupDownloadValidator;
import com.plateer.ec1.promotion.download.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Log4j2
@Validated
public class GeneralCupDownloadValidator implements CupDownloadValidator {

    private final CupDwlMapper cupDwlMapper;

    @Override
    public void isValid(CupDwlRequestVO cupDwlRequestVO){
        log.info("일반쿠폰 유효성 검사");
        CupInfoVO cupInfoVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
        isExistCupInfo(cupInfoVO);

        checkValid(cupInfoVO);
        log.info("일반쿠폰 유효성 완료");
    }

    private void checkValid(CupInfoVO cupInfoVO){
        isValidCupDwlPeriod(cupInfoVO);
        isValidCnt(cupInfoVO);
    }

    @Override
    public PRM0009Code getType() {
        return PRM0009Code.General_Cup;
    }
}
