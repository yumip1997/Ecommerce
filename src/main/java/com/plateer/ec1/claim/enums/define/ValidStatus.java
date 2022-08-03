package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ValidStatus {

    //일반상품주문취소완료 - 주문대기, 주문완료
    GCC(Arrays.asList(OPT0004Code.ORDER_COMPLETE), Arrays.asList(OPT0001Code.GENERAL)),
    //모바일쿠폰주문취소접수
    MCA(Arrays.asList(OPT0004Code.ORDER_COMPLETE), Arrays.asList(OPT0001Code.ECOUPON)),
    //모바일쿠폰주문취소완료
    MCC(Arrays.asList(OPT0004Code.ORDER_COMPLETE), Arrays.asList(OPT0001Code.ECOUPON)),

    //반품접수
    RA(Arrays.asList(OPT0004Code.DELIVERY_COMPLETE), Arrays.asList(OPT0001Code.GENERAL, OPT0001Code.ECOUPON)),
    //반품철회
    RW(Arrays.asList(OPT0004Code.RETURN_ACCEPT), Arrays.asList(OPT0001Code.GENERAL, OPT0001Code.ECOUPON)),

    //교환접수
    EA(Arrays.asList(OPT0004Code.DELIVERY_COMPLETE), Arrays.asList(OPT0001Code.GENERAL, OPT0001Code.ECOUPON)),
    //교환철회
    EW(Arrays.asList(OPT0004Code.EXCHANGE_ACCEPT), Arrays.asList(OPT0001Code.GENERAL, OPT0001Code.ECOUPON));

    private final List<OPT0004Code> validOrdPrgsScd;
    private final List<OPT0001Code> validSellTpCd;

}
