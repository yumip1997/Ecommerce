package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimMapper {

    String getClaimNo();

    List<ClaimView> getClaimViewList(List<ClaimGoodsInfo> claimGoodsInfoList);

}
