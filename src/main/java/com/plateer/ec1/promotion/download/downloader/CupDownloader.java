package com.plateer.ec1.promotion.download.downloader;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import org.springframework.beans.BeanUtils;

public interface CupDownloader extends CustomFactory<PRM0009Code> {

    void download(CupDwlRequestVO cupDwlRequestVO);

    default CcCpnIssueModel copyModel(CupDwlRequestVO cupDwlRequestVO){
        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.builder().build();
        BeanUtils.copyProperties(cupDwlRequestVO, ccCpnIssueModel);
        return ccCpnIssueModel;
    }

}
