package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.define.ClaimDefine.*;
import static com.plateer.ec1.order.enums.OPT0003Code.ORDER;

//TODO 삭제예정
@RequiredArgsConstructor
public enum OpClmUpdateCreator implements ClaimGroups<List<OpClmInfo>, ClaimGoodsInfo> {

    CANCEL_CNT((e) -> {
        if(!ORDER.code.equals(e.getOrdClmTpCd())) return e;
        ClaimGoodsInfo clone = e.clone();
        clone.setCnclCnt(clone.getOrdCnt());
        return clone;
    }, Arrays.asList(GCC, MCA)
    ),
    CANCEL_COMPLETE((e) -> {
        if(ORDER.code.equals(e.getOrdClmTpCd())) return e;
        ClaimGoodsInfo clone = e.clone();
        clone.setOrdPrgsScd(OPT0004Code.CANCEL_COMPLETE.code);
        clone.setOrdClmCmtDtime(LocalDateTime.now());
        return clone;
    }, Arrays.asList(MCC)),
    RTGS_CNT((e) -> {
        if(!ORDER.code.equals(e.getOrdClmTpCd())) return e;
        ClaimGoodsInfo clone = e.clone();
        clone.setRtgsCnt(clone.getOrdCnt());
        return clone;
    },  Arrays.asList(GRA, GEA)),
    RTGS_CNT_TO_ZERO((e) -> {
        if(ORDER.code.equals(e.getOrdClmTpCd())) return e;
        ClaimGoodsInfo clone = e.clone();
        clone.setRtgsCnt(0);
        return clone;
    },  Arrays.asList(GRW, GEW)),
    CANCEL_CNT_TO_ORD_CNT((e) -> {
        if(!ORDER.code.equals(e.getOrdClmTpCd())) return e;
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
    public List<OpClmInfo> create(ClaimDefine claimDefine, ClaimGoodsInfo claimGoodsInfo) {
        return Arrays.stream(OpClmUpdateCreator.values())
                .filter(e -> e.hasClaimDefine(claimDefine))
                .map(e -> e.updateFunction.apply(claimGoodsInfo))
                .filter(e -> e != claimGoodsInfo)
                .map(ClaimGoodsInfo::convertOpClmInfo)
                .collect(Collectors.toList());
    };

    private boolean hasClaimDefine(ClaimDefine claimDefine) {
        return this.groupingClaim().stream()
                .anyMatch(e -> e == claimDefine);
    }

}
