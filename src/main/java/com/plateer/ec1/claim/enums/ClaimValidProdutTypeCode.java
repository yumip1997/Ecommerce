package com.plateer.ec1.claim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ClaimValidProdutTypeCode {

    //일반상품주문취소완료
    GCC(Arrays.asList(ProductTypeCode.GENERAL.code)),
    //모바일쿠폰주문취소접수
    MCA(Arrays.asList(ProductTypeCode.ECOUPON.code)),
    //모바일쿠폰주문취소완료
    MCC(Arrays.asList(ProductTypeCode.ECOUPON.code)),

    //반품접수
    RA(Arrays.asList(ProductTypeCode.GENERAL.code, ProductTypeCode.ECOUPON.code)),
    //반품철회
    RW(Arrays.asList(ProductTypeCode.GENERAL.code, ProductTypeCode.ECOUPON.code)),

    //교환접수
    EA(Arrays.asList(ProductTypeCode.GENERAL.code)),
    //교환철회
    EW(Arrays.asList(ProductTypeCode.GENERAL.code));

    private final List<String> validProductTypeList;
}

