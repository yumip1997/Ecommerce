package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class ClaimGoodsInfo implements Cloneable{

    @NotEmpty
    private String ordNo;
    @NotEmpty
    private String ordGoodsNo;
    @NotEmpty
    private String ordItemNo;
    @NotNull
    private Integer ordSeq;
    @NotNull
    private Integer procSeq;
    @NotEmpty
    private String ordClmTpCd;
    @NotEmpty
    private String ordPrgsScd;
    @NotEmpty
    private String dvRvtCcd;
    @NotNull
    private Long ordAmt;
    @NotNull
    private Integer ordCnt;
    private int cnclCnt;
    private int rtgsCnt;
    @NotNull
    private Integer dvGrpNo;
    private LocalDateTime ordClmReqDtime;
    private LocalDateTime ordClmAcptDtime;
    private LocalDateTime ordClmCmtDtime;
    private String clmRsnCd;
    private String clmDtlRsnTt;
    private String clmNo;
    private int orgProcSeq;

    private int cnclReqCnt;
    private List<@Valid OrderBenefitBaseVO> benefitBaseVOList;

    public List<CupIssVO> toCupIssVOList(String mbrNo){
        return Optional.ofNullable(this.getBenefitBaseVOList())
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toCupIssVO(mbrNo))
                .collect(Collectors.toList());
    }

    public OpClmInfo convertOpClmInfo(){
        OpClmInfo opClmInfo = new OpClmInfo();
        BeanUtils.copyProperties(this, opClmInfo);
        return opClmInfo;
    }


    @Override
    public ClaimGoodsInfo clone() {
        try {
            return (ClaimGoodsInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
