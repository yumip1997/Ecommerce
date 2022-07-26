package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import lombok.*;

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

}
