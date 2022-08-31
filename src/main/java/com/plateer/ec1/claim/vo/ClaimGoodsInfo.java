package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class ClaimGoodsInfo implements Cloneable{

    @NotEmpty
    private String ordNo;
    private String ordGoodsNo;
    private String ordItemNo;
    @NotNull
    private Integer ordSeq;
    @NotNull
    private Integer procSeq;
    private String ordClmTpCd;
    @NotEmpty
    private String ordPrgsScd;
    private String dvRvtCcd;
    private Long ordAmt;
    private Integer ordCnt;
    private int cnclCnt;
    private int rtgsCnt;
    private Integer dvGrpNo;
    private LocalDateTime ordClmReqDtime;
    private LocalDateTime ordClmAcptDtime;
    private LocalDateTime ordClmCmtDtime;
    private String clmRsnCd;
    private String clmDtlRsnTt;
    private String clmNo;
    private int orgProcSeq;
    private String goodsSellTpCd;
    private List<OrderBenefitBaseVO> benefitBaseVOList;

    //TODO 장바구니 부분취소 일 때 emptyList 반환하도록 수정 예정
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

    public String getOrdNoDvGrpNo(){
        return this.ordNo + this.dvGrpNo;
    }

    public String getOrdNoOrdSeqProcSeq(){
        return this.ordNo + this.ordSeq + this.procSeq;
    }

    @Override
    public ClaimGoodsInfo clone() {
        try {
            //TODO LIST 생성하기
            return (ClaimGoodsInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
