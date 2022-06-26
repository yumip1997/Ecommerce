package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum DwlCupType {

    //일반쿠폰
    General_Cup("GC"),
    //오프라인쿠폰
    Offline_CUP("OC");

    private final String code;

    public static DwlCupType findDwlCupType(String code){
        return Arrays.stream(DwlCupType.values())
                .filter(dwlCupType -> dwlCupType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(PromotionException.INVALID_CUP_DWL_TYPE.getMSG()));
    }
}
