package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.OpClmBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private String ordClmTpCd;
    private String ordPrgsScd;
    private String dvRvtCcd;
    private long ordAmt;
    private int ordCnt;
    private int cnclCnt;
    private int rtgsCnt;
    private int dvGrpNo;
    private LocalDateTime ordClmReqDtime;
    private LocalDateTime ordClmAcptDtime;
    private LocalDateTime ordClmCmtDtime;
    private String clmRsnCd;
    private String clmDtlRsnTt;
    private String clmNo;
    private int orgProcSeq;

    private int cnclReqCnt;
    private List<OrderBenefitBaseVO> benefitBaseVOList;

    public List<OpClmInfo> toInsertOpClmInfoList(OpClmBase opClmBase, String clmNo){
        List<OpClmInfo> opClmInfoBaseList = new ArrayList<>();

        for(int i=0;i<opClmBase.getOpt0013CodeList().size();i++){
            OpClmInfo target = new OpClmInfo();
            BeanUtils.copyProperties(this, target);

            target.setProcSeq(this.getProcSeq() + (i + 1));
            target.setOrgProcSeq(this.getProcSeq());
            target.setOrdClmTpCd(opClmBase.getOpt0003Code());
            target.setOrdPrgsScd(opClmBase.getOrdPrgsScd());
            target.setDvRvtCcd(opClmBase.getOpt0013CodeList().get(i));
            target.setOrdClmCmtDtime(opClmBase.getClaimStatusType() == ClaimStatusType.COMPLETE ? LocalDateTime.now() : null);
            target.setClmNo(clmNo);

            opClmInfoBaseList.add(target);
        }

        return opClmInfoBaseList;
    }

    public List<OpOrdBnfRelInfo> toOpOrdBnfRelInfoList(String clmNo){
        return Optional.ofNullable(this.getBenefitBaseVOList())
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toOpOrdBnfRelInfo(clmNo))
                .collect(Collectors.toList());
    }

    public List<CupIssVO> toCupIssVOList(String mbrNo){
        return Optional.ofNullable(this.getBenefitBaseVOList())
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toCupIssVO(mbrNo))
                .collect(Collectors.toList());
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
