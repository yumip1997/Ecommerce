package com.plateer.ec1.promotion.download.downloader.impl;

import com.plateer.ec1.common.aop.LoginIdSetting;
import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.download.factory.CupDownloadValidatorFactory;
import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.mapper.CupDwlTrxMapper;
import com.plateer.ec1.promotion.download.validator.CupDownloadValidator;
import com.plateer.ec1.promotion.download.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import com.plateer.ec1.promotion.enums.PromotionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
@Log4j2
public class OffCupDownloader implements CupDownloader {

    private final CupDwlMapper cupDwlMapper;
    private final CupDwlTrxMapper cupDwlTrxMapper;
    private final CupDownloadValidatorFactory cupDownloadValidatorFactory;

    @Override
    public PRM0009Code getType() {
        return PRM0009Code.Offline_CUP;
    }

    @Override
    @LoginIdSetting
    @Transactional
    public void download(CupDwlRequestVO cupDwlRequestVO){
        log.info("오프라인 쿠폰 다운로드");
        setCupIssInfo(cupDwlRequestVO);

        CupDownloadValidator cupDownloadValidator = cupDownloadValidatorFactory.get(getType());
        cupDownloadValidator.isValid(cupDwlRequestVO);

        CcCpnIssueModel ccCpnIssueModel = copyModel(cupDwlRequestVO);

        cupDwlTrxMapper.updateCup(ccCpnIssueModel);
    }

    private void setCupIssInfo(CupDwlRequestVO cupDwlRequestVO){
        //발급번호와 프로모션 번호를 셋팅한다.
        CupInfoVO cupInfoVO = cupDwlMapper.getOfflineCupInfo(cupDwlRequestVO);

        if(ObjectUtils.isEmpty(cupInfoVO)) {
            throw new RuntimeException(PromotionException.INVALID_CPN_CERT_NO.getMSG());
        }

        cupDwlRequestVO.setPrmNo(cupInfoVO.getPrmNo());
        cupDwlRequestVO.setCpnIssNo(cupInfoVO.getCpnIssNo());
    }


}