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

import static com.plateer.ec1.claim.enums.ClaimStatusType.*;
import static com.plateer.ec1.order.enums.OPT0001Code.*;
import static com.plateer.ec1.order.enums.OPT0004Code.*;

@RequiredArgsConstructor
@Getter
public enum ClaimDefine {

    //일반상품주문취소완료
    GCC(GENERAL, CANCEL_COMPLETE, COMPLETE, OpClmBase.GCC, ValidOrdPrgs.BY_ORDER_COMPLETE),
    //모바일쿠폰주문취소접수
    MCA(ECOUPON, CANCEL_REQUEST, ACCEPT_WITHDRAWAL,OpClmBase.MCA, ValidOrdPrgs.ORDER_COMPLETE),
    //모바일쿠폰주문취소완료
    MCC(ECOUPON, CANCEL_COMPLETE, COMPLETE, OpClmBase.MCC, ValidOrdPrgs.ORDER_COMPLETE),

    //반품접수
    GRA(GENERAL, RETURN_ACCEPT, ACCEPT_WITHDRAWAL, OpClmBase.RA, ValidOrdPrgs.DELIVERY_COMPLETE),
    //반품철회
    GRW(GENERAL, RETURN_WITHDRAWAL, ACCEPT_WITHDRAWAL, OpClmBase.RW, ValidOrdPrgs.RETURN_ACCEPT),

    //교환접수
    GEA(GENERAL, EXCHANGE_ACCEPT, ACCEPT_WITHDRAWAL, OpClmBase.EA, ValidOrdPrgs.DELIVERY_COMPLETE),
    //교환철회
    GEW(GENERAL, EXCHANGE_WITHDRAWAL, ACCEPT_WITHDRAWAL, OpClmBase.EW, ValidOrdPrgs.EXCHANGE_ACCEPT);

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
