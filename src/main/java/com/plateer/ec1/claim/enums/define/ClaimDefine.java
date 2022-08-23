package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.excpetion.ExceptionMessage;
import com.plateer.ec1.order.enums.OPT0001Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimStatusType.ACCEPT_WITHDRAWAL;
import static com.plateer.ec1.claim.enums.ClaimStatusType.COMPLETE;
import static com.plateer.ec1.claim.enums.define.OpBnfBase.BNF_APPLY;
import static com.plateer.ec1.claim.enums.define.OpBnfBase.BNF_CANCEL;
import static com.plateer.ec1.claim.enums.define.OpClmInsertBase.*;
import static com.plateer.ec1.claim.enums.define.OpClmUpdateBase.*;
import static com.plateer.ec1.claim.enums.define.ValidOrdPrgs.*;
import static com.plateer.ec1.order.enums.OPT0001Code.ECOUPON;
import static com.plateer.ec1.order.enums.OPT0001Code.GENERAL;
import static com.plateer.ec1.order.enums.OPT0003Code.*;

@RequiredArgsConstructor
@Getter
public enum ClaimDefine {

    //일반상품주문취소완료
    GCC(GENERAL, toList(CANCEL.code), BY_ORDER_COMPLETE, OpClmInsertBase.GCC, CANCEL_CNT, BNF_CANCEL, COMPLETE),
    //모바일쿠폰주문취소접수
    MCA(ECOUPON, toList(CANCEL.code), IN_ORDER_COMPLETE, CA, CANCEL_CNT, BNF_CANCEL, ACCEPT_WITHDRAWAL),
    //모바일쿠폰주문취소완료
    MCC(ECOUPON, toList(CANCEL.code), IN_CANCEL_REQUEST, null, CANCEL_COMPLETE, null, COMPLETE),

    //반품접수
    GRA(GENERAL, toList(RETURN_ACCEPT.code), IN_DELIVERY_COMPLETE, RA, RTGS_CNT, BNF_CANCEL, ACCEPT_WITHDRAWAL),
    //반품철회
    GRW(GENERAL, toList(RETURN_WITHDRAWAL.code), IN_RETURN_ACCEPT, RW, RTGS_CANCEL_CNT, BNF_APPLY, ACCEPT_WITHDRAWAL),

    //교환접수
    GEA(GENERAL, toList(RETURN_ACCEPT.code, ORDER.code), IN_DELIVERY_COMPLETE, EA, RTGS_CNT, null, ACCEPT_WITHDRAWAL),
    //교환철회
    GEW(GENERAL, toList(RETURN_WITHDRAWAL.code, CANCEL.code), IN_EXCHANGE_ACCEPT, EW, RTGS_CANCEL_CNT, null, ACCEPT_WITHDRAWAL);

    private final OPT0001Code prdType;
    private final List<String> ordClmTypes;
    private final ValidOrdPrgs validOrdPrgs;
    private final OpClmInsertBase opClmInsertBase;
    private final OpClmUpdateBase opClmUpdateBase;
    private final OpBnfBase opBnfBase;
    private final ClaimStatusType claimStatusType;

    public static ClaimDefine of(ClaimRequestVO requestVO){
        return Arrays.stream(ClaimDefine.values())
                .filter(define -> define.isMatchPrdClaimType(requestVO))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.msg));
    }

    private boolean isMatchPrdClaimType(ClaimRequestVO req){
        return this.prdType.code.equals(req.getPrdType())
                && req.getOrdClmReqTypes().containsAll(this.ordClmTypes)
                && this.validOrdPrgs.isContains(req.getOrdPrgsType());
    }

    public List<String> getValidOrdPrgsStrList(){
        return this.validOrdPrgs.getValidOrdPrgsList();
    }

    public String getPrdTypeStr(){
        return this.prdType.code;
    }

    public boolean isWithrawalReq(){
        return this == ClaimDefine.GRW || this == ClaimDefine.GEW;
    }

    private static List<String> toList(String... codes) {
        return Arrays.asList(codes);
    }
}
