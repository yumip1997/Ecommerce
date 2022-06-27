package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.claim.enums.OrdPrgsScd;
import com.plateer.ec1.claim.enums.GoodsSellTpCd;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ValidStatus {

    //일반상품주문취소완료 - 주문대기, 주문완료
    GCC(Arrays.asList(OrdPrgsScd.ORDER_COMPLETE), Arrays.asList(GoodsSellTpCd.GENERAL)),
    //모바일쿠폰주문취소접수
    MCA(Arrays.asList(OrdPrgsScd.ORDER_COMPLETE), Arrays.asList(GoodsSellTpCd.ECOUPON)),
    //모바일쿠폰주문취소완료
    MCC(Arrays.asList(OrdPrgsScd.ORDER_COMPLETE), Arrays.asList(GoodsSellTpCd.ECOUPON)),

    //반품접수
    RA(Arrays.asList(OrdPrgsScd.DELIVERY_COMPLETE), Arrays.asList(GoodsSellTpCd.GENERAL, GoodsSellTpCd.ECOUPON)),
    //반품철회
    RW(Arrays.asList(OrdPrgsScd.RETURN_ACCEPT), Arrays.asList(GoodsSellTpCd.GENERAL, GoodsSellTpCd.ECOUPON)),

    //교환접수
    EA(Arrays.asList(OrdPrgsScd.DELIVERY_COMPLETE), Arrays.asList(GoodsSellTpCd.GENERAL, GoodsSellTpCd.ECOUPON)),
    //교환철회
    EW(Arrays.asList(OrdPrgsScd.EXCHANGE_ACCEPT), Arrays.asList(GoodsSellTpCd.GENERAL, GoodsSellTpCd.ECOUPON));

    private final List<OrdPrgsScd> validOrdPrgsScd;
    private final List<GoodsSellTpCd> validSellTpCd;

}
