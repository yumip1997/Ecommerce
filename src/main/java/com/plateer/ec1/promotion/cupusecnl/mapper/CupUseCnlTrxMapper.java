package com.plateer.ec1.promotion.cupusecnl.mapper;

import com.plateer.ec1.common.aop.LoginIdSetting;
import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupUseCnlTrxMapper {

    @LoginIdSetting
    void updateCupUsed(CcCpnIssueModel ccCpnIssueModel);

    @LoginIdSetting
    void insertOrgCup(CcCpnIssueModel ccCpnIssueModel);

}
