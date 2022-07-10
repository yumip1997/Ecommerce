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
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
@Validated
public class GeneralCupDownloader implements CupDownloader {
    
    private final CupDwlMapper cupDwlMapper;
    private final CupDwlTrxMapper cupDwlTrxMapper;

    @Override
    public CupDwlTypeCode getType() {
        return CupDwlTypeCode.GENERAL_CUP;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void download(CupDwlRequestVO cupDwlRequestVO){
        Optional<CupInfoVO> optCupInfoVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
        CupInfoVO cupInfoVO = optCupInfoVO.orElseThrow(() -> new RuntimeException(PromotionException.NOT_FIND_PRM.getMSG()));
        cupInfoVO.dwlValidate();

        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.convertModel(cupDwlRequestVO);
        cupDwlTrxMapper.insertCup(ccCpnIssueModel);
    }

}
