package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.vo.ClaimDeliveryInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.define.ClaimDefine.GEW;
import static com.plateer.ec1.claim.enums.define.ClaimDefine.GRW;

@RequiredArgsConstructor
public enum OpCostUpdateCreator implements ClaimCreator<List<OpOrdCostInfo>, List<ClaimDeliveryInfo>> {

    //적용 배송비에 대한 취소
    CNL_DV_AMT_UPDATE(e -> {
        if(OPT0005Code.APPLY.code.equals(e.getAplyCcd())) return null;
        return OpOrdCostInfo.builder()
                .ordCstNo(e.getOrdCstNo())
                .cnclDvAmt(e.getAplyDvAmt())
                .build();
    }, Arrays.asList(GRW, GEW));

    private final Function<ClaimDeliveryInfo, OpOrdCostInfo> opCostUpdateFunc;
    private final List<ClaimDefine> groups;

    @Override
    public List<ClaimDefine> groupingClaim() {
        return this.groups;
    }

    @Override
    public List<OpOrdCostInfo> create(List<ClaimDeliveryInfo> claimDeliveryInfo) {
        return claimDeliveryInfo.stream().map(this.opCostUpdateFunc)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<OpCostUpdateCreator> getCreators(ClaimDefine claimDefine){
        return Arrays.stream(OpCostUpdateCreator.values())
                .filter(e -> e.hasClaimDefine(claimDefine))
                .collect(Collectors.toList());
    }
}
