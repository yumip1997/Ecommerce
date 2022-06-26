package com.plateer.ec1.promotion.download.service;

import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.enums.DwlCupType;
import com.plateer.ec1.promotion.factory.DownloaderFactory;
import com.plateer.ec1.promotion.vo.request.CupDwlRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadService {

    private final DownloaderFactory downloaderFactory;

    public void download(CupDwlRequestVO cupDwlRequestVO) {
        CupDownloader cupDownloader = getCupDownloader(cupDwlRequestVO);
        cupDownloader.download(cupDwlRequestVO);
    }

    private CupDownloader getCupDownloader(CupDwlRequestVO cupDwlRequestVO){
        DwlCupType dwlCupType = DwlCupType.findDwlCupType(cupDwlRequestVO.getDwlCupType());
        return downloaderFactory.get(dwlCupType);
    }
}
