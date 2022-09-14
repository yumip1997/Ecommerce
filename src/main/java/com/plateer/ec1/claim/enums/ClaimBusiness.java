package com.plateer.ec1.claim.enums;

import com.plateer.ec1.claim.validation.groups.ClaimGroups;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.excpetion.ExceptionMessage;
import com.plateer.ec1.order.enums.OPT0001Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.plateer.ec1.claim.enums.ValidOrdPrgs.*;
import static com.plateer.ec1.order.enums.OPT0001Code.ECOUPON;
import static com.plateer.ec1.order.enums.OPT0001Code.GENERAL;
import static com.plateer.ec1.order.enums.OPT0003Code.*;

@RequiredArgsConstructor
@Getter
public enum ClaimBusiness {

    //일반상품주문취소완료
    GCC(GENERAL, toSet(CANCEL.code), BY_ORDER_COMPLETE, ClaimGroups.GnlOrdCnl.class),
    //모바일쿠폰주문취소접수
    MCA(ECOUPON, toSet(CANCEL.code), IN_ORDER_COMPLETE, ClaimGroups.MCupOrdCnlAcpt.class),
    //모바일쿠폰주문취소완료
    MCC(ECOUPON, toSet(CANCEL.code), IN_CANCEL_REQUEST, ClaimGroups.MCoupOrdCnlCmt.class),

    //반품접수
    GRA(GENERAL, toSet(RETURN_ACCEPT.code), IN_DELIVERY_COMPLETE, ClaimGroups.GnlRtrnAcpt.class),
    //반품철회
    GRW(GENERAL, toSet(RETURN_WITHDRAWAL.code), IN_RETURN_ACCEPT, ClaimGroups.GnlRtrnWtdwl.class),

    //교환접수
    GEA(GENERAL, toSet(EXCHANGE_ACCEPT.code), IN_DELIVERY_COMPLETE, ClaimGroups.GnlExchAcpt.class),
    //교환철회
    GEW(GENERAL, toSet(RETURN_WITHDRAWAL.code, CANCEL.code), IN_EXCHANGE_ACCEPT, ClaimGroups.GnlExchWtdwl.class);

    private final OPT0001Code prdType;
    private final Set<String> ordClmTypes;
    private final ValidOrdPrgs validOrdPrgs;
    private final Class<?> validationGroup;

    public static ClaimBusiness of(ClaimRequestVO requestVO){
        return Arrays.stream(ClaimBusiness.values())
                .filter(define -> define.isMatchPrdClaimType(requestVO))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.msg));
    }

    private boolean isMatchPrdClaimType(ClaimRequestVO req){
        return req.getPrdType().equals(this.prdType.code)
                && this.ordClmTypes.containsAll(req.getOrdClmReqTypes())
                && req.getOrdPrsgList().stream().allMatch(this.validOrdPrgs::isContains);
    }

    public String getPrdTypeStr(){
        return this.prdType.code;
    }

    private static Set<String> toSet(String... codes) {
        return new HashSet<>(Arrays.asList(codes));
    }
}
