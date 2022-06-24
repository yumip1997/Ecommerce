package com.plateer.ec1.claim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClaimValidStatusCode {

    //일반상품주문취소완료
    GCC(ClaimValidProcessCode.GCC, ClaimValidProdutTypeCode.GCC),
    //모바일쿠폰주문취소접수
    MCA(ClaimValidProcessCode.MCA, ClaimValidProdutTypeCode.MCA),
    //모바일쿠폰주문취소완료
    MCC(ClaimValidProcessCode.MCC, ClaimValidProdutTypeCode.MCC),

    //반품접수
    RA(ClaimValidProcessCode.RA, ClaimValidProdutTypeCode.RA),
    //반품철회
    RW(ClaimValidProcessCode.RW, ClaimValidProdutTypeCode.RW),

    //교환접수
    EA(ClaimValidProcessCode.EA, ClaimValidProdutTypeCode.EA),
    //교환철회
    EW(ClaimValidProcessCode.EW, ClaimValidProdutTypeCode.EW);

    private final ClaimValidProcessCode claimValidProcessCode;
    private final ClaimValidProdutTypeCode claimValidProdutTypeCode;
}
