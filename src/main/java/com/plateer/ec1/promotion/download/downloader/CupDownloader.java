package com.plateer.ec1.promotion.download.downloader;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;

import javax.validation.Valid;

public interface CupDownloader extends CustomFactory<PRM0009Code> {

    void download(@Valid CupDwlRequestVO cupDwlRequestVO);

}
