package com.plateer.ec1.common.model.order;

import com.plateer.ec1.common.model.BaseModel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpOrdBnfInfo extends BaseModel {

    private String ordBnfNo;
    private long ordBnfAmt;
    private long prmNo;
    private long cpnIssNo;
    private long ordCnclBnfAmt;
    private int degrCcd;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String cpnKndCd;

    public static OpOrdBnfInfo of(String ordBnfNo, long ordCnclBnfAmt){
        return OpOrdBnfInfo.builder()
                .ordBnfNo(ordBnfNo)
                .ordCnclBnfAmt(ordCnclBnfAmt)
                .build();
    }

}
