package com.plateer.ec1.claim.enums;

import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.excpetion.ExceptionMessage;
import com.plateer.ec1.order.enums.OPT0001Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ValidOrdPrgs.*;
import static com.plateer.ec1.order.enums.OPT0001Code.ECOUPON;
import static com.plateer.ec1.order.enums.OPT0001Code.GENERAL;
import static com.plateer.ec1.order.enums.OPT0003Code.*;

@RequiredArgsConstructor
@Getter
public enum ClaimBusiness {

    //일반상품주문취소완료
    GCC(GENERAL, toList(CANCEL.code), BY_ORDER_COMPLETE),
    //모바일쿠폰주문취소접수
    MCA(ECOUPON, toList(CANCEL.code), IN_ORDER_COMPLETE),
    //모바일쿠폰주문취소완료
    MCC(ECOUPON, toList(CANCEL.code), IN_CANCEL_REQUEST),

    //반품접수
    GRA(GENERAL, toList(RETURN_ACCEPT.code), IN_DELIVERY_COMPLETE),
    //반품철회
    GRW(GENERAL, toList(RETURN_WITHDRAWAL.code), IN_RETURN_ACCEPT),

    //교환접수
    GEA(GENERAL, toList(RETURN_ACCEPT.code, ORDER.code), IN_DELIVERY_COMPLETE),
    //교환철회
    GEW(GENERAL, toList(RETURN_WITHDRAWAL.code, CANCEL.code), IN_EXCHANGE_ACCEPT);

    private final OPT0001Code prdType;
    private final List<String> ordClmTypes;
    private final ValidOrdPrgs validOrdPrgs;

    public static ClaimBusiness of(ClaimRequestVO requestVO){
        return Arrays.stream(ClaimBusiness.values())
                .filter(define -> define.isMatchPrdClaimType(requestVO))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.NOT_VALID_CODE.msg));
    }

    //TODO 더 좋은 방법 생각해보기
    private boolean isMatchPrdClaimType(ClaimRequestVO req){
        return this.prdType.code.equals(req.getPrdType())
                && req.getOrdClmReqTypes().containsAll(this.ordClmTypes)
                && req.getOrdPrsgList().stream().allMatch(this.validOrdPrgs::isContains);
    }

    public String getPrdTypeStr(){
        return this.prdType.code;
    }

    private static List<String> toList(String... codes) {
        return Arrays.asList(codes);
    }
}
