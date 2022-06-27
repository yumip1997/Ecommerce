package com.plateer.ec1.claim.helper;

import com.plateer.ec1.claim.vo.ClaimBaseVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ClaimDataCreator {

    public static ClaimInsertBase makeClaimInsertBase(ClaimBaseVO claimBaseVO) {
        List<OpClmInfo> insertOpClmInfoList = claimBaseVO.getInsertOrderClaim();


        return ClaimInsertBase.builder().opClmInfoList(insertOpClmInfoList).build();
    }

    public static ClaimUpdateBase makeClaimUpadateBase(ClaimBaseVO claimBaseVO){
        OpClmInfo updateOpClmInfo = claimBaseVO.getUpdateCnt();

        return ClaimUpdateBase.builder().opClmInfo(updateOpClmInfo).build();
    }


}
