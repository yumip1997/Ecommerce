package com.plateer.ec1.common.model.order;

import com.plateer.ec1.claim.enums.OrderClaimBaseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderClaim implements Cloneable{

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
    private LocalDateTime completeDt;

    private String claimType;

    //TODO 주문 클레임 VO로 이동 예정
    public List<OrderClaim> getInsertOrderClaim(int procSeq)  {
        List<OrderClaim> orderClaimBaseList = OrderClaimBaseCode.valueOf(this.claimType).getInsertOrderClaim().get();

        List<OrderClaim> orderClaim = new ArrayList<>();
        for(int i=0;i<orderClaimBaseList.size();i++){
            //복사한다.
            OrderClaim copy = clone();
            //채번, 주문번호, 상품순번, 상품번호, 단품번호, 주문수랴, 취소수량 은 복사한 값 그대로 사용

            //처리순번(+1 셋팅)
            copy.setProcSeq(this.getProcSeq() + (i+procSeq));
            //이전처리순번 (복사한 처리 순번으로 셋팅)
            copy.setOrgProcSeq(this.getProcSeq());
            //클레임유형코드
            copy.setOrdClmTpCd(orderClaimBaseList.get(i).getOrdClmTpCd());
            //주문유형코드
            copy.setOrgProcSeq(orderClaimBaseList.get(i).getOrgProcSeq());
            //배송회수구분코드
            copy.setDvRvtCcd(orderClaimBaseList.get(i).getDvRvtCcd());
            //클레임완료일시
            copy.setCompleteDt(orderClaimBaseList.get(i).getCompleteDt());

        }
        return orderClaim;
    }

    public OrderClaim getUpdateOrderClaim(){
        //원주문의 취소 수량 or 원주문의 반품수량, 원클레임의 반품수량
        //클레임완료 일시 -> 반품완료, 교환완료

        return OrderClaim.builder().build();

    }

    @Override
    protected OrderClaim clone(){
        OrderClaim orderClaim = null;
        try{
            return orderClaim =  (OrderClaim) super.clone();
        }catch (CloneNotSupportedException c){
            throw new RuntimeException();
        }
    }
}
