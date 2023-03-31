package com.plateer.ec1.promotion.com.vo;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.common.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CupInfoVO extends BaseVO {

    private Long prmNo;
    private Long cpnIssNo;
    private String prmNm;
    private String cpnKindCd;
    private String degrCcd;
    private String useYn;
    private LocalDate dwlAvlEndDd;
    private LocalDateTime prmEndDt;
    private Long dwlPsbCnt;
    private Long psnDwlPsbCnt;
    private Long totalCnt;
    private Long mbrCnt;
    private String mbrNo;
    private LocalDateTime cpnUseDt;

    public CcCpnIssueModel toCcCpnIssueModel(String ordNo){
        return CcCpnIssueModel.builder()
                .cpnIssNo(this.cpnIssNo)
                .mbrNo(this.mbrNo)
                .prmNo(this.prmNo)
                .ordNo(ordNo)
                .build();
    }

}
