package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PRM0009Code {

    //일반쿠폰다운로드
    General_Cup("10"),
    //오프라인쿠폰다운로드
    Offline_CUP("40");

    private final String code;

    public static PRM0009Code findDwlCupType(String code){
        if(General_Cup.code.equals(code)){
            return General_Cup;
        }

        if(Offline_CUP.code.equals(code)){
            return Offline_CUP;
        }

        throw new IllegalArgumentException(PromotionException.INVALID_CUP_DWL_TYPE.getMSG());
    }
}
