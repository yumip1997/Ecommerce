package com.plateer.ec1.promotion.download.validator.impl;

import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.validator.CupDownloadValidator;
import com.plateer.ec1.promotion.download.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import com.plateer.ec1.promotion.enums.PromotionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
@Log4j2
public class GeneralCupDownloadValidator implements CupDownloadValidator {

    private final CupDwlMapper cupDwlMapper;

    @Override
    public void isValid(CupDwlRequestVO cupDwlRequestVO){
        log.info("일반쿠폰 유효성 검사");
        checkNotEmpty(cupDwlRequestVO);

        CupInfoVO cupInfoVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
        isExistCupInfo(cupInfoVO);

        checkValid(cupInfoVO);
        log.info("일반쿠폰 유효성 완료");
    }

    private void checkNotEmpty(CupDwlRequestVO cupDwlRequestVO){
        isNotEmptyPrmNo(cupDwlRequestVO);
        isNotEmptyMbrNo(cupDwlRequestVO);
    }

    private void isNotEmptyPrmNo(CupDwlRequestVO cupDwlRequestVO){
        boolean isValid = !ObjectUtils.isEmpty(cupDwlRequestVO.getPrmNo());
        if(isValid) return;

        throw new RuntimeException(PromotionException.NULL_CUP_PRM_NO.getMSG());
    }

    private void checkValid(CupInfoVO cupInfoVO){
        isValidPeriod(cupInfoVO);
        isValidCnt(cupInfoVO);
    }

    @Override
    public PRM0009Code getType() {
        return PRM0009Code.General_Cup;
    }
}
