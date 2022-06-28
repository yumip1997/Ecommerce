package com.plateer.ec1.promotion.com.vo;

import com.plateer.ec1.common.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CupInfoVO extends BaseVO {

    private Long prmNo;
    private Long cpnIssNo;
    private String prmNm;
    private String useYn;
    private Timestamp dwlAvlEndDd;
    private Timestamp prmEndDt;
    private Long dwlPsbCnt;
    private Long psnDwlPsbCnt;
    private Long totalCnt;
    private Long mbrCnt;
    private String mbrNo;

}
