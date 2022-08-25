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


    GENERAL_ORDER_CANCEL(CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, DELIVERY.code, IntUnaryOperator.identity(), LocalDateTime::now
            , Collections.singletonList(GCC)),
    ORDER_CANCEL_REQUEST(CANCEL.code, OPT0004Code.CANCEL_REQUEST.code, DELIVERY.code, IntUnaryOperator.identity(), () -> null
            , Collections.singletonList(MCA)),
    ORDER_CANCEL_COMPLETE(CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, DELIVERY.code, IntUnaryOperator.identity(), LocalDateTime::now
            , Collections.singletonList(MCC)),

    RETURN_ACCEPT_RETV(RETURN_ACCEPT.code, OPT0004Code.RETURN_ACCEPT.code, RETRIEVE.code, (dvp) -> dvp + 1, () -> null
            , Collections.singletonList(GRA)),
    RETURN_WITHDRAWAL_RETV(RETURN_WITHDRAWAL.code, OPT0004Code.RETURN_WITHDRAWAL.code, RETRIEVE.code, IntUnaryOperator.identity(), LocalDateTime::now
            , Collections.singletonList(GRW)),

    EXCHANGE_ACCEPT_REVT(EXCHANGE_ACCEPT.code, OPT0004Code.EXCHANGE_ACCEPT.code, RETRIEVE.code, (dvp) -> dvp + 1, () -> null,
            Collections.singletonList(GEA)),
    EXCHANGE_ACCEPT_DV(EXCHANGE_ACCEPT.code, OPT0004Code.EXCHANGE_ACCEPT.code, DELIVERY.code, (dvp) -> dvp + 1, () -> null,
            Collections.singletonList(GEA)),

    EXCHANGE_WITHDRAWAL_REVT(RETURN_WITHDRAWAL.code, OPT0004Code.RETURN_WITHDRAWAL.code, RETRIEVE.code, IntUnaryOperator.identity(), LocalDateTime::now,
            Collections.singletonList(GEW)),
    EXCAHNGE_WITHDRWAL_DV(RETURN_WITHDRAWAL.code, OPT0004Code.RETURN_WITHDRAWAL.code, RETRIEVE.code, IntUnaryOperator.identity(), LocalDateTime::now,
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
