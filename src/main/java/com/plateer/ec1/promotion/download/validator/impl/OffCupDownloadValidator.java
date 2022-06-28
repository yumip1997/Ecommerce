package com.plateer.ec1.promotion.download.validator.impl;

import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.validator.CupDownloadValidator;
import com.plateer.ec1.promotion.download.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class OffCupDownloadValidator implements CupDownloadValidator {

    private final CupDwlMapper cupDwlMapper;

    @Override
    public void isValid(CupDwlRequestVO cupDwlRequestVO){
        CupInfoVO cupInfoVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
        checkValid(cupInfoVO);
    }

    private void checkValid(CupInfoVO cupInfoVO){
        isValidCupDwlPeriod(cupInfoVO);
        isValidCnt(cupInfoVO);
    }

    @Override
    public PRM0009Code getType() {
        return PRM0009Code.Offline_CUP;
    }
}
