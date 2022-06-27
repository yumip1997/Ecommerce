package com.plateer.ec1.promotion.download.downloader;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;

public interface CupDownloader extends CustomFactory<PRM0009Code> {

    void download(CupDwlRequestVO cupDwlRequestVO) throws Exception;

}
