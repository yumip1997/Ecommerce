package com.plateer.ec1.claim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ClaimValidProcessCode {

    //일반상품주문취소완료 - 주문대기, 주문완료
    GCC(Arrays.asList(OrdPrgsScdCode.ORDER_COMPLETE.code)),
    //모바일쿠폰주문취소접수
    MCA(Arrays.asList(OrdPrgsScdCode.ORDER_COMPLETE.code)),
    //모바일쿠폰주문취소완료
    MCC(Arrays.asList(OrdPrgsScdCode.ORDER_COMPLETE.code)),

    //반품접수
    RA(Arrays.asList(OrdPrgsScdCode.DELIVERY_COMPLETE.code)),
    //반품철회
    RW(Arrays.asList(OrdPrgsScdCode.RETURN_ACCEPT.code)),

    //교환접수
    EA(Arrays.asList(OrdPrgsScdCode.DELIVERY_COMPLETE.code)),
    //교환철회
    EW(Arrays.asList(OrdPrgsScdCode.EXCHANGE_ACCEPT.code));

    private final List<String> validOrderStatusList;
}
