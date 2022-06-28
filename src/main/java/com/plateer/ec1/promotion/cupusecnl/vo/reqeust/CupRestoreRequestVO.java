package com.plateer.ec1.promotion.cupusecnl.vo.reqeust;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CupRestoreRequestVO {

    @NotNull(message = "쿠폰 복원 시 복원할 쿠폰의 발급번호는 필수입니다.")
    private Long cpnIssNo;
}
