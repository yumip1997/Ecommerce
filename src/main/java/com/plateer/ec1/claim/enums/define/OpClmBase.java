package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.enums.OPT0013Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum OpClmBase {

    //일반상품주문취소완료
    GCC(OPT0003Code.CANCEL, OPT0004Code.CANCEL_COMPLETE, Arrays.asList(OPT0013Code.DELIVERY)),
    //모바일쿠폰주문취소접수
    MCA(OPT0003Code.CANCEL, OPT0004Code.CANCEL_REQUEST, Arrays.asList(OPT0013Code.DELIVERY)),
    //모바일쿠폰주문취소완료
    MCC(OPT0003Code.CANCEL, OPT0004Code.CANCEL_COMPLETE, Arrays.asList(OPT0013Code.DELIVERY)),

    //반품접수
    RA(OPT0003Code.RETURN, OPT0004Code.RETURN_ACCEPT,Arrays.asList(OPT0013Code.RETRIEVE)),
    //반품철회
    RW(OPT0003Code.RETURN_WITHDRAWAL, OPT0004Code.RETURN_WITHDRAWAL,Arrays.asList(OPT0013Code.RETRIEVE)),

    //교환접수
    EA(OPT0003Code.EXCHANGE_ACCEPT, OPT0004Code.EXCHANGE_ACCEPT,Arrays.asList(OPT0013Code.DELIVERY, OPT0013Code.RETRIEVE)),
    //교환철회
    EW(OPT0003Code.EXCHANGE_WITHDRAWAL, OPT0004Code.EXCHANGE_WITHDRAWAL, Arrays.asList(OPT0013Code.DELIVERY, OPT0013Code.RETRIEVE));

    private final OPT0003Code opt0003Code;
    private final OPT0004Code ordPrgsScd;
    private final List<OPT0013Code> opt0013CodeList;



}
