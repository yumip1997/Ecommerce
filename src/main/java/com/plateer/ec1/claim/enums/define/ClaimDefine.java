package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.excpetion.ExceptionMessage;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ClaimDefine {

    //일반상품주문취소완료
    GCC(OPT0001Code.GENERAL, OPT0004Code.CANCEL_COMPLETE, ClaimStatusType.COMPLETE, OpClmBase.GCC, ValidOrdPrgs.BY_ORDER_COMPLETE),
    //모바일쿠폰주문취소접수
    MCA(OPT0001Code.ECOUPON, OPT0004Code.CANCEL_REQUEST, ClaimStatusType.ACCEPT_WITHDRAWAL,OpClmBase.MCA, ValidOrdPrgs.ORDER_COMPLETE),
    //모바일쿠폰주문취소완료
    MCC(OPT0001Code.ECOUPON, OPT0004Code.CANCEL_COMPLETE, ClaimStatusType.COMPLETE, OpClmBase.MCC, ValidOrdPrgs.ORDER_COMPLETE),

    //반품접수
    GRA(OPT0001Code.GENERAL, OPT0004Code.RETURN_ACCEPT, ClaimStatusType.ACCEPT_WITHDRAWAL, OpClmBase.RA, ValidOrdPrgs.DELIVERY_COMPLETE),
    //반품철회
    GRW(OPT0001Code.GENERAL, OPT0004Code.RETURN_WITHDRAWAL, ClaimStatusType.ACCEPT_WITHDRAWAL, OpClmBase.RW, ValidOrdPrgs.RETURN_ACCEPT),

    //교환접수
    GEA(OPT0001Code.GENERAL, OPT0004Code.EXCHANGE_ACCEPT, ClaimStatusType.ACCEPT_WITHDRAWAL, OpClmBase.EA, ValidOrdPrgs.DELIVERY_COMPLETE),
    //교환철회
    GEW(OPT0001Code.GENERAL, OPT0004Code.EXCHANGE_WITHDRAWAL, ClaimStatusType.ACCEPT_WITHDRAWAL, OpClmBase.EW, ValidOrdPrgs.EXCHANGE_ACCEPT);

    private final OPT0001Code prdType;
    private final OPT0004Code claimReqType;
    private final ClaimStatusType claimStatusType;
    private final OpClmBase opClmBase;
    private final ValidOrdPrgs validOrdPrgs;

    public static ClaimDefine of(ClaimRequestVO requestVO){
        return Arrays.stream(ClaimDefine.values())
                .filter(define -> define.isMatchPrdClaimType(requestVO))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.msg));
    }

    private boolean isMatchPrdClaimType(ClaimRequestVO req){
        return this.prdType.code.equals(req.getPrdType()) && this.claimReqType.code.equals(req.getClaimReqType());
    }

    public List<String> getValidOrdPrgsStrList(){
        return this.validOrdPrgs.getValidOrdPrgsList();
    }

    public String getPrdTypeStr(){
        return this.prdType.code;
    }


}
