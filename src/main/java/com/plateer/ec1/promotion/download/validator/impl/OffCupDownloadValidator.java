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
    public void isValid(CupDwlRequestVO cupDwlRequestVO) throws Exception {
        checkNotEmpty(cupDwlRequestVO);

        CupInfoVO offlineCupInfo = cupDwlMapper.getOfflineCupInfo(cupDwlRequestVO);
        isExistOffCup(offlineCupInfo);

        CupInfoVO cupInfoVO = cupDwlMapper.getCupDwlInfo(CupDwlRequestVO.builder().prmNo(offlineCupInfo.getPrmNo()).build());
        isExistCupInfo(cupInfoVO);

        checkValid(cupInfoVO);
    }

    private void checkNotEmpty(CupDwlRequestVO cupDwlRequestVO) throws Exception {
        isNotEmptyMbrNo(cupDwlRequestVO);
        isNotEmptyCpnCertNo(cupDwlRequestVO);
    }

    private void isNotEmptyCpnCertNo(CupDwlRequestVO cupDwlRequestVO) throws Exception {
        if(!ObjectUtils.isEmpty(cupDwlRequestVO.getCpnCertNo())) return;

        throw new Exception(PromotionException.NULL_CPN_CERT_NO.getMSG());
    }

    private void isExistOffCup(CupInfoVO cupInfoVO) throws Exception {
        if(!ObjectUtils.isEmpty(cupInfoVO)) return;

        throw new Exception(PromotionException.INVALID_CPN_CERT_NO.getMSG());
    }

    private void checkValid(CupInfoVO cupInfoVO) throws Exception {
        isValidPeriod(cupInfoVO);
        isValidCnt(cupInfoVO);
    }
}
