package com.plateer.ec1.promotion.cupusecnl.vo.reqeust;

import com.plateer.ec1.promotion.com.validation.CupRestore;
import com.plateer.ec1.promotion.com.validation.CupUse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CupIssVO {

    @NotNull(message = "쿠폰발급번호는 필수입니다.", groups = {CupUse.class, CupRestore.class})
    private Long cpnIssNo;
    @NotNull(message = "주문번호는 필수입니다.", groups = {CupUse.class})
    private String ordNo;
    @NotNull(message = "회원번호는 필수입니다.", groups = {CupUse.class, CupRestore.class})
    private String mbrNo;


}
