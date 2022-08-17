package com.plateer.ec1.promotion.cupusecnl.mapper;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CupUseCnlTrxMapper {

    void updateCupUsed(List<CcCpnIssueModel> ccCpnIssueModel);

    void insertOrgCup(List<CcCpnIssueModel> ccCpnIssueModel);

}
