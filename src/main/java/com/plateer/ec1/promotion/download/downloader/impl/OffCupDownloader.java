package com.plateer.ec1.promotion.download.downloader.impl;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.mapper.CupDwlTrxMapper;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.CupDwlTypeCode;
import com.plateer.ec1.promotion.enums.PromotionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class OffCupDownloader implements CupDownloader {

    private final CupDwlMapper cupDwlMapper;
    private final CupDwlTrxMapper cupDwlTrxMapper;

    @Override
    public CupDwlTypeCode getType() {
        return CupDwlTypeCode.OFFLINE_CUP;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void download(CupDwlRequestVO cupDwlRequestVO){
        setCupIssInfo(cupDwlRequestVO);

        Optional<CupInfoVO> optCupInfoVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
        CupInfoVO cupInfoVO = optCupInfoVO.orElseThrow(() -> new RuntimeException(PromotionException.NOT_FIND_PRM.getMSG()));
        cupInfoVO.dwlValidate();

        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.convertModel(cupDwlRequestVO);

        cupDwlTrxMapper.updateCup(ccCpnIssueModel);
    }

    private void setCupIssInfo(CupDwlRequestVO cupDwlRequestVO){
        Optional<CupInfoVO> optCupInfoVO = cupDwlMapper.getOfflineCupInfo(cupDwlRequestVO);
        CupInfoVO cupInfoVO = optCupInfoVO.orElseThrow(() -> new RuntimeException(PromotionException.INVALID_CPN_CERT_NO.getMSG()));

        cupDwlRequestVO.setPrmNo(cupInfoVO.getPrmNo());
        cupDwlRequestVO.setCpnIssNo(cupInfoVO.getCpnIssNo());
    }


}
