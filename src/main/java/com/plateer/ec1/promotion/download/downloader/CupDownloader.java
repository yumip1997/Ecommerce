package com.plateer.ec1.promotion.download.downloader;

import com.plateer.ec1.common.factory.StrategyType;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.CupDwlTypeCode;

import javax.validation.Valid;

public interface CupDownloader extends StrategyType<CupDwlTypeCode> {

    void download(@Valid CupDwlRequestVO cupDwlRequestVO);

}
