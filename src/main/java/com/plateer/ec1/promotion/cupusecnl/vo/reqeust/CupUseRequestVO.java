package com.plateer.ec1.promotion.cupusecnl.vo.reqeust;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CupUseRequestVO{

    @NotNull(message = "쿠폰 사용 시 사용할 쿠폰의 발급번호는 필수입니다.")
    private Long cpnIssNo;
    @NotNull(message = "쿠폰 사용 시 주문 번호는 필수입니다.")
    private String ordNo;
    @NotNull(message = "쿠폰 사용 시 회원 번호는 필수입니다.")
    private String mbrNo;

}
