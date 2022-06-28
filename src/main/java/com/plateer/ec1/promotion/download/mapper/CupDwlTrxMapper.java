package com.plateer.ec1.promotion.download.mapper;

import com.plateer.ec1.common.aop.LoginIdSetting;
import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupDwlTrxMapper {

    @LoginIdSetting
    void insertCup(CcCpnIssueModel ccCpnIssueModel);

    @LoginIdSetting
    void updateCup(CcCpnIssueModel ccCpnIssueModel);

}
