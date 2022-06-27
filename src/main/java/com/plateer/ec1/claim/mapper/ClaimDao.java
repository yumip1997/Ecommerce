package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.claim.vo.ClaimBaseVO;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimDao {

    OpClmInfo selectClaim(ClaimBaseVO claimBaseVO);

    void insertOrderClaim(List<OpClmInfo> opClmInfoList);
    void insertOrderCost(List<OpOrdCostInfo> opOrdCostInfoList);
    void insertOrderBenefitRelation(OpOrdBnfRelInfo opOrdBnfRelInfo);

    void updateOrderClaim(OpClmInfo opClmInfo);
    void updateOrgOrderCnt(OpClmInfo opClmInfo);
    void updateOrderBenefit(OpOrdBnfInfo orderBenefit);


}
