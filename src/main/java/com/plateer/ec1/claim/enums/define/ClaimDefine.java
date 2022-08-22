package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.excpetion.ExceptionMessage;
import com.plateer.ec1.order.enums.OPT0001Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimStatusType.*;
import static com.plateer.ec1.claim.enums.define.OpBnfBase.BNF_APPLY;
import static com.plateer.ec1.claim.enums.define.OpBnfBase.BNF_CANCEL;
import static com.plateer.ec1.claim.enums.define.OpClmInsertBase.*;
import static com.plateer.ec1.claim.enums.define.OpClmUpdateBase.*;
import static com.plateer.ec1.claim.enums.define.ValidOrdPrgs.*;
import static com.plateer.ec1.order.enums.OPT0001Code.ECOUPON;
import static com.plateer.ec1.order.enums.OPT0001Code.GENERAL;

@RequiredArgsConstructor
@Getter
public enum ClaimDefine {

    //일반상품주문취소완료
    GCC(GENERAL, BY_ORDER_COMPLETE, OpClmInsertBase.GCC, CANCEL, BNF_CANCEL, COMPLETE),
    //모바일쿠폰주문취소접수
    MCA(ECOUPON, ORDER_COMPLETE, CA, CANCEL, BNF_CANCEL, ACCEPT_WITHDRAWAL),
    //모바일쿠폰주문취소완료
    MCC(ECOUPON, ORDER_COMPLETE, CC, CANCEL_COMPLETE, null, COMPLETE),

    //반품접수
    GRA(GENERAL, DELIVERY_COMPLETE, RA, RETURN, BNF_CANCEL, ACCEPT_WITHDRAWAL),
    //반품철회
    GRW(GENERAL, RETURN_ACCEPT, RW, RETURN_WITHDRAWAL, BNF_APPLY, ACCEPT_WITHDRAWAL),

    //교환접수
    GEA(GENERAL, DELIVERY_COMPLETE, EA, RETURN, null, ACCEPT_WITHDRAWAL),
    //교환철회
    GEW(GENERAL, EXCHANGE_ACCEPT, EW, RETURN_WITHDRAWAL, null, ACCEPT_WITHDRAWAL);

    private final OPT0001Code prdType;
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
        return this.prdType.code.equals(req.getPrdType()) && this.validOrdPrgs.isContains(req.getOrdPrgsType());
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
}
