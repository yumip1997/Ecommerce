package com.plateer.ec1.promotion.download.factory;

import com.plateer.ec1.common.factory.FactoryTemplate;
import com.plateer.ec1.promotion.download.downloader.CupDownloader;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CupDownloaderFactory extends FactoryTemplate<PRM0009Code, CupDownloader> {

    public CupDownloaderFactory(List<CupDownloader> list) {
        super(list);
    }

}
