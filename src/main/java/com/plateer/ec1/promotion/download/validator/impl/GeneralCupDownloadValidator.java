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
public class GeneralCupDownloadValidator implements CupDownloadValidator {

    private final CupDwlMapper cupDwlMapper;

    @Override
    public void isValid(CupDwlRequestVO cupDwlRequestVO) throws Exception {
        checkNotEmpty(cupDwlRequestVO);

        CupInfoVO cupInfoVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
        isExistCupInfo(cupInfoVO);

        checkValid(cupInfoVO);
    }

    private void checkNotEmpty(CupDwlRequestVO cupDwlRequestVO) throws Exception {
        isNotEmptyPrmNo(cupDwlRequestVO);
        isNotEmptyMbrNo(cupDwlRequestVO);
    }

    private void isNotEmptyPrmNo(CupDwlRequestVO cupDwlRequestVO) throws Exception {
        boolean isValid = !ObjectUtils.isEmpty(cupDwlRequestVO.getPrmNo());
        if(isValid) return;

        throw new Exception(PromotionException.NULL_CUP_PRM_NO.getMSG());
    }

    private void checkValid(CupInfoVO cupInfoVO) throws Exception {
        isValidPeriod(cupInfoVO);
        isValidCnt(cupInfoVO);
    }

    @Override
    public PRM0009Code getType() {
        return PRM0009Code.General_Cup;
    }
}
