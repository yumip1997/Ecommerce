package com.plateer.ec1.common.model.promotion;

import com.plateer.ec1.common.model.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CcMbrPntModel extends BaseModel {

    private Long pntHstSeq;
    private String mbrNo;
    private String svUseCcd;
    private Long svUseAmt;
    private Long pntBlc;
    private String ordNo;
    private String payNo;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

}
