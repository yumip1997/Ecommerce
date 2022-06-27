package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.claim.enums.DvRvtCcd;
import com.plateer.ec1.claim.enums.OrdClmTpCd;
import com.plateer.ec1.claim.enums.OrdPrgsScd;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum OpClmBase {

    //일반상품주문취소완료
    GCC(OrdClmTpCd.CANCEL, OrdPrgsScd.CANCEL_COMPLETE, Arrays.asList(DvRvtCcd.DELIVERY)),
    //모바일쿠폰주문취소접수
    MCA(OrdClmTpCd.CANCEL, OrdPrgsScd.CANCEL_REQUEST, Arrays.asList(DvRvtCcd.DELIVERY)),
    //모바일쿠폰주문취소완료
    MCC(OrdClmTpCd.CANCEL, OrdPrgsScd.CANCEL_COMPLETE, Arrays.asList(DvRvtCcd.DELIVERY)),

    //반품접수
    RA(OrdClmTpCd.RETURN, OrdPrgsScd.RETURN_ACCEPT,Arrays.asList(DvRvtCcd.RETRIEVE)),
    //반품철회
    RW(OrdClmTpCd.RETURN_WITHDRAWAL, OrdPrgsScd.RETURN_WITHDRAWAL,Arrays.asList(DvRvtCcd.RETRIEVE)),

    //교환접수
    EA(OrdClmTpCd.EXCHANGE_ACCEPT, OrdPrgsScd.EXCHANGE_ACCEPT,Arrays.asList(DvRvtCcd.DELIVERY, DvRvtCcd.RETRIEVE)),
    //교환철회
    EW(OrdClmTpCd.EXCHANGE_WITHDRAWAL, OrdPrgsScd.EXCHANGE_WITHDRAWAL, Arrays.asList(DvRvtCcd.DELIVERY, DvRvtCcd.RETRIEVE));

    private final OrdClmTpCd ordClmTpCd;
    private final OrdPrgsScd ordPrgsScd;
    private final List<DvRvtCcd> dvRvtCcdList;



}
