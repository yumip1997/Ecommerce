package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ValidCode {

    //일반상품주문취소완료
    GCC(OPT0001Code.GENERAL.code, Arrays.asList(OPT0004Code.ORDER_WAITING.code, OPT0004Code.ORDER_COMPLETE.code)),
    //모바일쿠폰주문취소접수
    MCA(OPT0001Code.ECOUPON.code, Arrays.asList(OPT0004Code.ORDER_COMPLETE.code)),
    //모바일쿠폰주문취소완료
    MCC(OPT0001Code.ECOUPON.code, Arrays.asList(OPT0004Code.ORDER_COMPLETE.code)),

    //반품접수
    GRA(OPT0001Code.GENERAL.code, Arrays.asList(OPT0004Code.DELIVERY_COMPLETE.code)),
    //반품철회
    GRW(OPT0001Code.GENERAL.code, Arrays.asList(OPT0004Code.RETURN_ACCEPT.code)),

    //교환접수
    GEA(OPT0001Code.GENERAL.code, Arrays.asList(OPT0004Code.DELIVERY_COMPLETE.code)),
    //교환철회
    GEW(OPT0001Code.GENERAL.code, Arrays.asList(OPT0004Code.EXCHANGE_ACCEPT.code));

    private final String validPrdCode;
    private final List<String> validOrdPrgsCode;
}
