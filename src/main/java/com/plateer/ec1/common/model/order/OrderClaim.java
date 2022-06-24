package com.plateer.ec1.common.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderClaim {

    //주문번호
    private String ordNo;
    //상품번호
    private String ordGoodsNo;
    //처리순번
    private long procSeq;
    //주문진행상태코드
    private String ordPrgsScd;
    //주문클레임유형코드
    private String ordClmTpCd;
    //배송회수구분코드드
    private String dvRvtCcd;
    //주문금액
    private long ordAmt;
    //주문수량
    private int ordCnt;
    //취소수량
    private int cnclCnt;
    //반품수량
    private int rtgsCnt;
    //배송그룹번호
    private int dvGrpNo;
    //원처리순번
    private long orgProcSeq;

    private String claimType;

    //TODO 수정필요!
    public List<OrderClaim> getInsertOrderClaim(){
        //복사한다.
        return new ArrayList<>();
    };

    public OrderClaim getUpdateOrderClaim(){
        return OrderClaim.builder().build();
    }

}
