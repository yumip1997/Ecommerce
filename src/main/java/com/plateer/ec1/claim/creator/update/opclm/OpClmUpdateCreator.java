package com.plateer.ec1.claim.creator.update.opclm;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpClmInfo;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface OpClmUpdateCreator extends ClaimDataCreator<List<OpClmInfo>> {

    default List<OpClmInfo> createOpClmInfoList(ClaimView claimView, Function<ClaimGoodsInfo, OpClmInfo> function){
        return claimView.getClaimGoodsInfoList().stream()
                .map(function)
                .collect(Collectors.toList());
    }

}
