package com.plateer.ec1.promotion.download.downloader.impl;

import com.plateer.ec1.common.aop.LoginIdSetting;
import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.com.validator.CupInfoValidator;
import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.mapper.CupDwlTrxMapper;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import com.plateer.ec1.promotion.enums.PromotionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
@Log4j2
public class OffCupDownloader implements CupDownloader {

    private final CupDwlMapper cupDwlMapper;
    private final CupDwlTrxMapper cupDwlTrxMapper;

    @Override
    public PRM0009Code getType() {
        return PRM0009Code.Offline_CUP;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void download(CupDwlRequestVO cupDwlRequestVO){
        setCupIssInfo(cupDwlRequestVO);

        CupInfoVO cupInfoVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
        cupInfoVO.dwlValidate();

        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.convertModel(cupDwlRequestVO);

        cupDwlTrxMapper.updateCup(ccCpnIssueModel);
    }

    private void setCupIssInfo(CupDwlRequestVO cupDwlRequestVO){
        CupInfoVO cupInfoVO = cupDwlMapper.getOfflineCupInfo(cupDwlRequestVO);

        if(ObjectUtils.isEmpty(cupInfoVO)) {
            throw new RuntimeException(PromotionException.INVALID_CPN_CERT_NO.getMSG());
        }

        cupDwlRequestVO.setPrmNo(cupInfoVO.getPrmNo());
        cupDwlRequestVO.setCpnIssNo(cupInfoVO.getCpnIssNo());
    }


}
