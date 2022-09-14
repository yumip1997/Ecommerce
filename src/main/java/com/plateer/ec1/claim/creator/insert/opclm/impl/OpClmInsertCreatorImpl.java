package com.plateer.ec1.claim.creator.insert.opclm.impl;

import com.plateer.ec1.claim.creator.insert.opclm.OpClmInsertCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
public enum OpClmInsertCreatorImpl implements OpClmInsertCreator {

    GNL_ORD_CNL(toList(CANCEL.code), toList(OPT0004Code.CANCEL_COMPLETE.code), toList(DELIVERY.code), IntUnaryOperator.identity(), LocalDateTime::now
                , Collections.singletonList(GCC)),
    ORL_CNL_REQ(toList(CANCEL.code), toList(OPT0004Code.CANCEL_REQUEST.code), toList(DELIVERY.code), IntUnaryOperator.identity(), () -> null
                , Collections.singletonList(MCA)),

    RTRN_ACPT(toList(RETURN_ACCEPT.code), toList(OPT0004Code.RETURN_ACCEPT.code), toList(RETRIEVE.code), (dvp) -> dvp + 1, () -> null
                , Collections.singletonList(GRA)),
    RTRN_WTDWL(toList(RETURN_WITHDRAWAL.code), toList(OPT0004Code.RETURN_WITHDRAWAL.code), toList(RETRIEVE.code), IntUnaryOperator.identity(), LocalDateTime::now
                , Collections.singletonList(GRW)),

    EX_ACPT(toList(EXCHANGE_ACCEPT.code, EXCHANGE_ACCEPT.code), toList(OPT0004Code.EXCHANGE_ACCEPT.code, OPT0004Code.EXCHANGE_ACCEPT.code)
            , toList(RETRIEVE.code, DELIVERY.code), (dvp) -> dvp + 1, () -> null
            , Collections.singletonList(GEA)),
    EX_WTDW(
            toList(RETURN_WITHDRAWAL.code, CANCEL.code), toList(OPT0004Code.RETURN_WITHDRAWAL.code, OPT0004Code.CANCEL_COMPLETE.code)
            , toList(RETRIEVE.code, DELIVERY.code), IntUnaryOperator.identity(), LocalDateTime::now
            , Collections.singletonList(GEW));


    private final List<String> ordClmTpCdList;
    private final List<String> ordPrgsScdList;
    private final List<String> dvRctCcdList;
    private final IntUnaryOperator dvpGrpOperator;
    private final Supplier<LocalDateTime> cmtDtimeSupplier;
    private final List<ClaimBusiness> groups;

    @Override
    public List<OpClmInfo> create(ClaimView claimView) {
        return claimView.getClaimGoodsInfoList().stream()
                .map(this::createOpClmInfo)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return this.groups;
    }

    private List<OpClmInfo> createOpClmInfo(ClaimGoodsInfo claimGoodsInfo) {
        List<OpClmInfo> opClmInfoList = new ArrayList<>();
        for(int i=0;i<dvRctCcdList.size();i++){
            ClaimGoodsInfo clone = claimGoodsInfo.clone();
            clone.setOrdClmTpCd(ordClmTpCdList.get(i));
            clone.setOrdPrgsScd(ordPrgsScdList.get(i));
            clone.setDvRvtCcd(dvRctCcdList.get(i));
            clone.setDvGrpNo(dvpGrpOperator.applyAsInt(clone.getDvGrpNo()) + (i+1));
            clone.setOrdClmCmtDtime(cmtDtimeSupplier.get());
            opClmInfoList.add(clone.convertOpClmInfo());
        }
        return opClmInfoList;
    }

    private static List<String> toList(String... codes){
        return Arrays.asList(codes);
    }


}
