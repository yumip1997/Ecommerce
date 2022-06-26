package com.plateer.ec1.promotion.download.downloader;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.enums.DwlCupType;
import com.plateer.ec1.promotion.apply.vo.request.CupDwlRequestVO;

public interface CupDownloader extends CustomFactory<DwlCupType> {

    void download(CupDwlRequestVO cupDwlRequestVO);

}
