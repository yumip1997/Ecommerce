package com.plateer.ec1.promotion.download.service;

import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.download.factory.CupDownloaderFactory;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.CupDwlTypeCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class DownloadService {

    private final CupDownloaderFactory cupDownloaderFactory;

    public void download(CupDwlRequestVO cupDwlRequestVO){
        CupDownloader cupDownloader = getCupDownloader(cupDwlRequestVO);

        try{
            cupDownloader.download(cupDwlRequestVO);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private CupDownloader getCupDownloader(CupDwlRequestVO cupDwlRequestVO){
        CupDwlTypeCode pRM0009Code = CupDwlTypeCode.of(cupDwlRequestVO.getDwlCupType());
        return cupDownloaderFactory.get(pRM0009Code);
    }
}
