package com.plateer.ec1.common.model.promotion;

import com.plateer.ec1.common.model.BaseModel;
import com.plateer.ec1.common.vo.BaseVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CcCpnIssueModel extends BaseModel {

    private Long cpnIssNo;
    private String mbrNo;
    private LocalDateTime cpnUseDt;
    private Long orgCpnIssNo;
    private String cpnCertNo;
    private String ordNo;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private Long prmNo;

}
