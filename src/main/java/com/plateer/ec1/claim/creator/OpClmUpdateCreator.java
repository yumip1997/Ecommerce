package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

@RequiredArgsConstructor
public enum OpClmUpdateCreator implements ClaimCreator<List<OpClmInfo>, List<ClaimGoodsInfo>> {

    CANCEL_CNT(e ->
            OpClmInfo.builder().ordNo(e.getOrdNo())
                    .ordSeq(e.getOrdSeq())
                    .procSeq(e.getProcSeq())
                    .cnclCnt(e.getOrdCnt())
                    .build()
            , Arrays.asList(GCC, MCA)),
    CANCEL_COMPLETE(e ->
            OpClmInfo.builder().ordNo(e.getOrdNo())
                    .ordSeq(e.getOrdSeq())
                    .procSeq(e.getProcSeq())
                    .ordPrgsScd(OPT0004Code.CANCEL_COMPLETE.code)
                    .ordClmCmtDtime(LocalDateTime.now())
                    .build()
            , Collections.singletonList(MCC)),
    RTGS_CNT(e ->
            OpClmInfo.builder().ordNo(e.getOrdNo())
                    .ordSeq(e.getOrdSeq())
                    .procSeq(e.getProcSeq())
                    .rtgsCnt(e.getOrdCnt())
                    .build()
            , Arrays.asList(GRA, GEA)),
    RTGS_CNT_TO_ZERO((e) -> {
        if (!OPT0003Code.ORDER.code.equals(e.getOrdClmTpCd())) return null;
        return OpClmInfo.builder().ordNo(e.getOrdNo())
                .ordSeq(e.getOrdSeq())
                .procSeq(e.getProcSeq())
                .rtgsCnt(0)
                .build();
    }, Arrays.asList(GRW, GEW)),
    CANCEL_CNT_TO_ORD_CNT((e) -> {
        if (OPT0003Code.ORDER.code.equals(e.getOrdClmTpCd())) return null;
        return OpClmInfo.builder().ordNo(e.getOrdNo())
                .ordSeq(e.getOrdSeq())
                .procSeq(e.getProcSeq())
                .cnclCnt(e.getOrdCnt())
                .build();
    }, Arrays.asList(GRW, GEW));

    private final Function<ClaimGoodsInfo, OpClmInfo> updateFunction;  //접수일 경우 원주문에 대한 수정(원 주문 취소 or 반품 수량 수정)
    private final List<ClaimBusiness> groups;

    @Override
    public List<ClaimBusiness> getTypes() {
        return this.groups;
    }

    @Override
    public List<OpClmInfo> create(List<ClaimGoodsInfo> claimGoodsInfoList) {
        return claimGoodsInfoList.stream()
                .map(this.updateFunction)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<OpClmUpdateCreator> getCreators(ClaimBusiness claimBusiness) {
        return Arrays.stream(OpClmUpdateCreator.values())
                .filter(e -> e.hasType(claimBusiness))
                .collect(Collectors.toList());
    }


}
