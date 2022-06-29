package com.plateer.ec1.common.model.promotion;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CcPrmBaseModel {

    private Long prmNo;
    private String prmNm;
    private String prmKindCd;
    private String prmPriodCcd;
    private LocalDateTime prmStrtDt;
    private LocalDateTime prmEndDt;
    private Long prmStdDd;
    private String empYn;
    private String dcCcd;
    private Long dcVal;
    private Long minPurAmt;
    private Long maxDcAmt;
    private String useYn;
    private String rmk;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

}
