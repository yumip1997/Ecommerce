package com.plateer.ec1.promotion.vo.request;

import com.plateer.ec1.common.vo.BaseVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CupDwlRequestVO extends BaseVO {

    @NotNull(message = "프로모션 번호는 필수입니다.")
    private long prmNo;

    @NotNull(message = "회원 번포는 필수입니다.")
    private String mbrNo;

    private String dwlCupType;

}
