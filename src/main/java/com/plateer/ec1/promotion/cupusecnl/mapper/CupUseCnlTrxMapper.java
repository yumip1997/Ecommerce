package com.plateer.ec1.promotion.cupusecnl.mapper;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupUseCnlTrxMapper {

    void updateCupUsed(CcCpnIssueModel ccCpnIssueModel);
    Long insertOrgCup(CcCpnIssueModel ccCpnIssueModel);
}
