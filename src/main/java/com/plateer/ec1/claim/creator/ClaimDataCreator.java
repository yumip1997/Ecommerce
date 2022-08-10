package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClaimDataCreator {

    public static ClaimInsertBase createClaimInsertBase(ClaimRequestVO claimRequestVO) {
        List<OpClmInfo> insertOpClmInfoList = new ArrayList<>();

        return ClaimInsertBase.builder().opClmInfoList(insertOpClmInfoList).build();
    }

    public static ClaimUpdateBase createClaimUpdateBase(ClaimRequestVO claimRequestVO){
        return new ClaimUpdateBase();
    }


}
