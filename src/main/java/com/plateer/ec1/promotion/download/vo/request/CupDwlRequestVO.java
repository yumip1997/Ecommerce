package com.plateer.ec1.promotion.download.vo.request;

import com.plateer.ec1.common.vo.BaseVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class CupDwlRequestVO extends BaseVO {

    private Long prmNo;
    @NotNull(message = "쿠폰 다운로도 시 회원 번호는 필수 입니다.")
    private String mbrNo;
    private String cpnCertNo;
    private String dwlCupType;
    private Long cpnIssNo;

}
