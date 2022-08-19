package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public enum OpClmUpdateBase {

    CANCEL((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setCnclCnt(clone.getOrdCnt());
        return clone;
    }, Function.identity()),
    CANCEL_COMPLETE((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setOrdPrgsScd(OPT0004Code.CANCEL_COMPLETE.code);
        clone.setOrdClmCmtDtime(LocalDateTime.now());
        return clone;
    }, Function.identity()),
    RETURN((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setRtgsCnt(clone.getOrdCnt());
        return clone;
    }, Function.identity()),
    RETURN_WITHDRAWAL((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setRtgsCnt(e.getOrdCnt());
        return clone;
    }, (e) ->{
        ClaimGoodsInfo clone = e.clone();
        clone.setCnclCnt(e.getOrdCnt());
        return clone;
    });

    private final Function<ClaimGoodsInfo, ClaimGoodsInfo> orgOrdUpdateFunction;
    //TODO 수정 필요
    private final Function<ClaimGoodsInfo, ClaimGoodsInfo> orgClmUpdateFunction;

}
