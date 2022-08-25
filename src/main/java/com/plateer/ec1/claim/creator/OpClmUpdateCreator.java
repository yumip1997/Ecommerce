package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.define.ClaimDefine.*;

@RequiredArgsConstructor
public enum OpClmUpdateCreator implements ClaimCreator<List<OpClmInfo>, List<ClaimGoodsInfo>> {

    CANCEL_CNT((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setCnclCnt(clone.getOrdCnt());
        return clone;
    }, Arrays.asList(GCC, MCA)
    ),
    CANCEL_COMPLETE((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setOrdPrgsScd(OPT0004Code.CANCEL_COMPLETE.code);
        clone.setOrdClmCmtDtime(LocalDateTime.now());
        return clone;
    }, Arrays.asList(MCC)),
    RTGS_CNT((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setRtgsCnt(clone.getOrdCnt());
        return clone;
    },  Arrays.asList(GRA, GEA)),
    RTGS_CNT_TO_ZERO((e) -> {
        if(!OPT0003Code.ORDER.code.equals(e.getOrdClmTpCd())) return null;

        ClaimGoodsInfo clone = e.clone();
        clone.setRtgsCnt(0);
        return clone;
    },  Arrays.asList(GRW, GEW)),
    CANCEL_CNT_TO_ORD_CNT((e) -> {
        if(OPT0003Code.ORDER.code.equals(e.getOrdClmTpCd())) return null;

        ClaimGoodsInfo clone = e.clone();
        clone.setCnclCnt(e.getOrdCnt());
        return clone;
    }, Arrays.asList(GRW, GEW));

    private final Function<ClaimGoodsInfo, ClaimGoodsInfo> updateFunction;  //접수일 경우 원주문에 대한 수정(원 주문 취소 or 반품 수량 수정)
    private final List<ClaimDefine> groups;

    @Override
    public List<ClaimDefine> groupingClaim() {
        return this.groups;
    }

    @Override
    public List<OpClmInfo> create(List<ClaimGoodsInfo> claimGoodsInfoList) {
        return claimGoodsInfoList.stream()
                .map(this.updateFunction)
                .filter(Objects::nonNull)
                .map(ClaimGoodsInfo::convertOpClmInfo)
                .collect(Collectors.toList());
    }

    public static List<OpClmUpdateCreator> getCreators(ClaimDefine claimDefine){
        return Arrays.stream(OpClmUpdateCreator.values())
                .filter(e -> e.hasClaimDefine(claimDefine))
                .collect(Collectors.toList());
    }


}
