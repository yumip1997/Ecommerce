package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

/*
주문, 클레임 데이터 생성 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdClmCreationVO<T,U> extends OrderClaimBaseVO {

    private T insertData;
    private U updateData;
    private Exception exception;

    @Builder
    public OrdClmCreationVO(T insertData, U updateData, Exception e, String ordNo, String clmNo){
        super(ordNo, clmNo);
        this.insertData = insertData;
        this.updateData = updateData;
        this.exception = e;
    }

}
