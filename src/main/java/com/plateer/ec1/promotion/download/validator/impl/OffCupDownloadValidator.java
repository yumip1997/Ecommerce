package com.plateer.ec1.promotion.download.validator.impl;

import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.validator.CupDownloadValidator;
import com.plateer.ec1.promotion.download.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import com.plateer.ec1.promotion.enums.PromotionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class OffCupDownloadValidator implements CupDownloadValidator {

    private final CupDwlMapper cupDwlMapper;

    @Override
    public PRM0009Code getType() {
        return PRM0009Code.Offline_CUP;
    }

    @Override
    public void isValid(CupDwlRequestVO cupDwlRequestVO){
        checkNotEmpty(cupDwlRequestVO);

        CupInfoVO offlineCupInfo = cupDwlMapper.getOfflineCupInfo(cupDwlRequestVO);
        isExistOffCup(offlineCupInfo);

        CupInfoVO cupInfoVO = cupDwlMapper.getCupDwlInfo(CupDwlRequestVO.builder().prmNo(offlineCupInfo.getPrmNo()).build());
        isExistCupInfo(cupInfoVO);

        checkValid(cupInfoVO);
    }

    private void checkNotEmpty(CupDwlRequestVO cupDwlRequestVO){
        isNotEmptyMbrNo(cupDwlRequestVO);
        isNotEmptyCpnCertNo(cupDwlRequestVO);
    }

    private void isNotEmptyCpnCertNo(CupDwlRequestVO cupDwlRequestVO){
        if(!ObjectUtils.isEmpty(cupDwlRequestVO.getCpnCertNo())) return;

        throw new RuntimeException(PromotionException.NULL_CPN_CERT_NO.getMSG());
    }

    private void isExistOffCup(CupInfoVO cupInfoVO){
        if(!ObjectUtils.isEmpty(cupInfoVO)) return;

        throw new RuntimeException(PromotionException.INVALID_CPN_CERT_NO.getMSG());
    }

    private void checkValid(CupInfoVO cupInfoVO){
        isValidPeriod(cupInfoVO);
        isValidCnt(cupInfoVO);
    }
}
