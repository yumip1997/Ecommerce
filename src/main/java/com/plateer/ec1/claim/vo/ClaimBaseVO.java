package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.DvRvtCcd;
import com.plateer.ec1.claim.enums.define.OpClmBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ClaimBaseVO{
    //TODO 클레임 타입 삭제 예정..
    private String claimType;
    private String goodsSellTpCd;   //판매유형코드
    private String ordNo;
    private String ordGoodsNo;
    private String ordItemNo;
    private Integer ordSeq;
    private Integer procSeq;
    private String ordClmTpCd;
    private String ordPrgsScd;
    private String dvRvtCcd;
    private Long ordAmt;
    private Integer ordCnt;
    private Integer cnclCnt;
    private Integer rtgsCnt;
    private Integer dvGrpNo;
    private LocalDateTime ordClmReqDtime;
    private LocalDateTime ordClmAcptDtime;
    private LocalDateTime ordClmCmtDtime;
    private String clmRsnCd;
    private String clmDtlRsnTt;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String clmNo;
    private Integer orgProcSeq;

    public List<OpClmInfo> getInsertOrderClaim(){
        List<OpClmInfo> opClmInfoBaseList = new ArrayList<>();
        //TODO 수정 필요
        OpClmBase opClmBase = OpClmBase.valueOf(claimType);
        List<DvRvtCcd> dvRvtCcdList = opClmBase.getDvRvtCcdList();

        for(int i=0;i<dvRvtCcdList.size();i++){
            //TODO clone으로 바꾸기
            OpClmInfo target = OpClmInfo.builder().build();
            BeanUtils.copyProperties(this, target);

            //TODO 처리 순번을 받아서 +i하기
            target.setProcSeq(this.getProcSeq() + (i +1));
            target.setOrgProcSeq(this.getProcSeq());
            target.setOrdClmTpCd(opClmBase.getOrdClmTpCd().getCode());
            target.setOrdPrgsScd(opClmBase.getOrdPrgsScd().getCode());
            target.setDvRvtCcd(opClmBase.getDvRvtCcdList().get(i).getCode());

            opClmInfoBaseList.add(target);
        }

        return opClmInfoBaseList;
    }

    public OpClmInfo getUpdateCnt(){
        return null;
    }

    public OpClmInfo getUpdateOrdPrgsCd(){
        return null;
    }
}
