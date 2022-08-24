package com.plateer.ec1.common.model.order;

import com.plateer.ec1.common.model.BaseModel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpOrdBase extends BaseModel {

    private String ordNo;
    private String mbrNo;
    private String ordTpCd;
    private String ordSysCcd;
    private LocalDateTime ordReqDtime;
    private LocalDateTime ordCmtDtime;
    private String ordNm;
    private String ordSellNo;
    private String ordAddr;
    private String ordAddrDtl;
    private String ordEmail;
    private String rfndBnkCk;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModrId;
    private String sysModDtim;

}
