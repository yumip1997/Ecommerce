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
    GCC(OPT0003Code.CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, Arrays.asList(OPT0013Code.DELIVERY.code)),
    //모바일쿠폰주문취소접수
    MCA(OPT0003Code.CANCEL.code, OPT0004Code.CANCEL_REQUEST.code, Arrays.asList(OPT0013Code.DELIVERY.code)),
    //모바일쿠폰주문취소완료
    MCC(OPT0003Code.CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, Arrays.asList(OPT0013Code.DELIVERY.code)),

    //반품접수
    RA(OPT0003Code.RETURN.code, OPT0004Code.RETURN_ACCEPT.code, Arrays.asList(OPT0013Code.RETRIEVE.code)),
    //반품철회
    RW(OPT0003Code.RETURN_WITHDRAWAL.code, OPT0004Code.RETURN_WITHDRAWAL.code, Arrays.asList(OPT0013Code.RETRIEVE.code)),

    //교환접수
    EA(OPT0003Code.EXCHANGE_ACCEPT.code, OPT0004Code.EXCHANGE_ACCEPT.code, Arrays.asList(OPT0013Code.DELIVERY.code, OPT0013Code.RETRIEVE.code)),
    //교환철회
    EW(OPT0003Code.EXCHANGE_WITHDRAWAL.code, OPT0004Code.EXCHANGE_WITHDRAWAL.code, Arrays.asList(OPT0013Code.DELIVERY.code, OPT0013Code.RETRIEVE.code));

    private final String opt0003Code;
    private final String ordPrgsScd;
    private final List<String> opt0013CodeList;



}
