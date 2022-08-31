package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimMapper {

    String getClaimNo();

    ClaimView getClaimView(ClaimRequestVO claimRequestVO);

    List<ClaimGoodsInfo> getOrgOpClmList(List<ClaimGoodsInfo> claimGoodsInfoList);

}
