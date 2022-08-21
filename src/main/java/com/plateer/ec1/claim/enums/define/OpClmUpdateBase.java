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

    // 접수, 철회 -> 취소 or 반품 수량 update
    // 완료 -> 주문진행상태, 클레임완료 일시 update
    CANCEL((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setCnclCnt(clone.getOrdCnt());
        return clone;
    }, Function.identity()),
    CANCEL_COMPLETE(Function.identity(), (e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setOrdPrgsScd(OPT0004Code.CANCEL_COMPLETE.code);
        clone.setOrdClmCmtDtime(LocalDateTime.now());
        return clone;
    }),
    RETURN((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setRtgsCnt(clone.getOrdCnt());
        return clone;
    }, Function.identity()),
    RETURN_WITHDRAWAL((e) -> {
        ClaimGoodsInfo clone = e.clone();
        clone.setRtgsCnt(0);
        return clone;
    }, (e) ->{
        ClaimGoodsInfo clone = e.clone();
        clone.setCnclCnt(e.getOrdCnt());
        return clone;
    });

    private final Function<ClaimGoodsInfo, ClaimGoodsInfo> orgOrdUpdateFunction;  //접수일 경우 원주문에 대한 수정(원 주문 취소 or 반품 수량 수정)
    private final Function<ClaimGoodsInfo, ClaimGoodsInfo> orgClmUpdateFunction; //철회, 완료 경우 원접수클레임에 대한 수정

    public ClaimGoodsInfo getOrgOrdUpdate(ClaimGoodsInfo orgOrd){
        return this.orgOrdUpdateFunction.apply(orgOrd);
    }

    public ClaimGoodsInfo getOrgClmUpdate(ClaimGoodsInfo orgClm){
        return this.getOrgClmUpdateFunction().apply(orgClm);
    }

}
