package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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

    public OpClmInfo convertOpClmInfo(){
        OpClmInfo opClmInfo = new OpClmInfo();
        BeanUtils.copyProperties(this, opClmInfo);
        return opClmInfo;
    }

    private OpClmInfo toUpdateOpClmInfo(){
        return OpClmInfo.builder().ordNo(this.ordNo)
                .ordSeq(this.ordSeq)
                .procSeq(this.procSeq)
                .build();
    }

    public OpClmInfo toOpClmInfoOfCancelCnt(){
        OpClmInfo opClmInfo = toUpdateOpClmInfo();
        opClmInfo.setCnclCnt(this.ordCnt);
        return opClmInfo;
    }

    public OpClmInfo toOpClmInfoOfRtgsCnt(){
        OpClmInfo opClmInfo = toUpdateOpClmInfo();
        opClmInfo.setRtgsCnt(this.ordCnt);
        return opClmInfo;
    }

    public OpClmInfo toOpClmInfoOfZeroRtgsCnt(){
        OpClmInfo opClmInfo = toUpdateOpClmInfo();
        opClmInfo.setRtgsCnt(0);
        return opClmInfo;
    }

    public OpClmInfo toOpClmInfoCancelComplete(){
        OpClmInfo opClmInfo = toUpdateOpClmInfo();
        opClmInfo.setOrdPrgsScd(OPT0004Code.CANCEL_COMPLETE.code);
        opClmInfo.setOrdClmCmtDtime(LocalDateTime.now());
        return opClmInfo;
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
