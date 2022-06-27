package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum PRM0009Code {

    //일반쿠폰다운로드
    General_Cup("10"),
    //오프라인쿠폰다운로드
    Offline_CUP("40");

    private final String code;

    public static PRM0009Code findDwlCupType(String code){
        return Arrays.stream(PRM0009Code.values())
                .filter(dwlCupType -> dwlCupType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(PromotionException.INVALID_CUP_DWL_TYPE.getMSG()));
    }
}
