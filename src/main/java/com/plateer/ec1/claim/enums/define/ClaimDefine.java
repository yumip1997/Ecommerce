package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.excpetion.ExceptionMessage;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ClaimDefine {

    //일반상품주문취소완료
    GCC(OPT0001Code.GENERAL.code, OPT0004Code.CANCEL_COMPLETE.code, Boolean.TRUE, ClaimStatusType.COMPLETE, ValidCode.GCC, OpClmBase.GCC),
    //모바일쿠폰주문취소접수
    MCA(OPT0001Code.ECOUPON.code, OPT0004Code.CANCEL_REQUEST.code, Boolean.TRUE, ClaimStatusType.ACCEPT_WITHDRAWAL, ValidCode.MCA, OpClmBase.MCA),
    //모바일쿠폰주문취소완료
    MCC(OPT0001Code.ECOUPON.code, OPT0004Code.CANCEL_COMPLETE.code, Boolean.FALSE, ClaimStatusType.COMPLETE, ValidCode.MCC, OpClmBase.MCC),

    //반품접수
    GRA(OPT0001Code.GENERAL.code, OPT0004Code.RETURN_ACCEPT.code, Boolean.TRUE, ClaimStatusType.ACCEPT_WITHDRAWAL, ValidCode.GRA, OpClmBase.RA),
    //반품철회
    GRW(OPT0001Code.GENERAL.code, OPT0004Code.RETURN_WITHDRAWAL.code, Boolean.TRUE, ClaimStatusType.ACCEPT_WITHDRAWAL, ValidCode.GRW, OpClmBase.RW),

    //교환접수
    GEA(OPT0001Code.GENERAL.code, OPT0004Code.EXCHANGE_ACCEPT.code, Boolean.TRUE, ClaimStatusType.ACCEPT_WITHDRAWAL, ValidCode.GEA, OpClmBase.EA),
    //교환철회
    GEW(OPT0001Code.GENERAL.code, OPT0004Code.EXCHANGE_WITHDRAWAL.code, Boolean.TRUE, ClaimStatusType.ACCEPT_WITHDRAWAL, ValidCode.GEA, OpClmBase.EW);

    private final String prdType;
    private final String claimReqType;
    private final boolean claimNumFlag;
    private final ClaimStatusType claimStatusType;
    private final ValidCode validCode;
    private final OpClmBase opClmBase;

    public static ClaimDefine of(ClaimRequestVO requestVO){
        return Arrays.stream(ClaimDefine.values())
                .filter(define -> define.isMatchPrdClaimType(requestVO))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.msg));
    }

    private boolean isMatchPrdClaimType(ClaimRequestVO req){
        return this.prdType.equals(req.getPrdType()) && this.claimReqType.equals(req.getClaimReqType());
    }


}
