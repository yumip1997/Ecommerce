package com.plateer.ec1.promotion.download.factory;

import com.plateer.ec1.common.factory.AbstractFactory;
import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.enums.CupDwlTypeCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CupDownloaderFactory extends AbstractFactory<CupDwlTypeCode, CupDownloader> {

    public CupDownloaderFactory(List<CupDownloader> list) {
        super(list);
    }

}
