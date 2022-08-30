package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;
import static com.plateer.ec1.order.enums.OPT0003Code.*;
import static com.plateer.ec1.order.enums.OPT0013Code.DELIVERY;
import static com.plateer.ec1.order.enums.OPT0013Code.RETRIEVE;

@RequiredArgsConstructor
public enum OpClmInsertCreator implements ClaimCreator<OpClmInfo, ClaimGoodsInfo> {


    GNL_ORD_CNL(CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, DELIVERY.code, IntUnaryOperator.identity(), LocalDateTime::now
            , Collections.singletonList(GCC)),
    ORL_CNL_REQ(CANCEL.code, OPT0004Code.CANCEL_REQUEST.code, DELIVERY.code, IntUnaryOperator.identity(), () -> null
            , Collections.singletonList(MCA)),
    ORD_CNL_CMT(CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, DELIVERY.code, IntUnaryOperator.identity(), LocalDateTime::now
            , Collections.singletonList(MCC)),

    RTN_APT(RETURN_ACCEPT.code, OPT0004Code.RETURN_ACCEPT.code, RETRIEVE.code, (dvp) -> dvp + 1, () -> null
            , Collections.singletonList(GRA)),
    RTN_WTD(RETURN_WITHDRAWAL.code, OPT0004Code.RETURN_WITHDRAWAL.code, RETRIEVE.code, IntUnaryOperator.identity(), LocalDateTime::now
            , Collections.singletonList(GRW)),

    EX_ACP_RTV(RETURN_ACCEPT.code, OPT0004Code.RETURN_ACCEPT.code, RETRIEVE.code, (dvp) -> dvp + 1, () -> null,
            Collections.singletonList(GEA)),
    EX_ACP_DV(RETURN_ACCEPT.code, OPT0004Code.RETURN_ACCEPT.code, DELIVERY.code, (dvp) -> dvp + 2, () -> null,
            Collections.singletonList(GEA)),

    EX_WTD_RTV(RETURN_WITHDRAWAL.code, OPT0004Code.RETURN_WITHDRAWAL.code, RETRIEVE.code, IntUnaryOperator.identity(), LocalDateTime::now,
            Collections.singletonList(GEW)),
    EX_WTD_DV(CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, DELIVERY.code, IntUnaryOperator.identity(), LocalDateTime::now,
            Collections.singletonList(GEW));


    private final String ordClmTpCd;
    private final String ordPrgsScd;
    private final String dvRctCcd;
    private final IntUnaryOperator dvpGrpOperator;
    private final Supplier<LocalDateTime> cmtDtimeSupplier;
    private final List<ClaimBusiness> groups;

    @Override
    public List<ClaimBusiness> getTypes() {
        return this.groups;
    }

    @Override
    public OpClmInfo create(ClaimGoodsInfo claimGoodsInfo) {
        ClaimGoodsInfo clone = claimGoodsInfo.clone();
        clone.setOrdClmTpCd(this.ordClmTpCd);
        clone.setOrdPrgsScd(this.ordPrgsScd);
        clone.setDvRvtCcd(this.dvRctCcd);
        clone.setDvGrpNo(this.dvpGrpOperator.applyAsInt(clone.getDvGrpNo()));
        clone.setOrdClmCmtDtime(this.cmtDtimeSupplier.get());
        clone.setOrgProcSeq(clone.getProcSeq());
        return clone.convertOpClmInfo();
    }

    public static List<OpClmInsertCreator> getCreators(ClaimBusiness claimBusiness){
        return Arrays.stream(OpClmInsertCreator.values())
                .filter(e -> e.hasClaimDefine(claimBusiness))
                .collect(Collectors.toList());
    }

}
