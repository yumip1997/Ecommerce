package com.plateer.ec1.promotion.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.enums.DwlCupType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DownloaderFactory extends FactoryTemplate<DwlCupType, CupDownloader> {

    public DownloaderFactory(List<CupDownloader> list) {
        super(list);
    }
}
