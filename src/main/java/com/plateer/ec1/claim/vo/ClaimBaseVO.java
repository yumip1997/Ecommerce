package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.define.OpClmBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
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

    private String ordNo;
    private String ordGoodsNo;
    private String ordItemNo;
    private int ordSeq;
    private int procSeq;
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

    public List<OpClmInfo> getInsertOrderClaim(OpClmBase opClmBase){
        List<OpClmInfo> opClmInfoBaseList = new ArrayList<>();
        //TODO 수정 필요
        List<String> dvRvtCcdList = opClmBase.getOpt0013CodeList();

        for(int i=0;i<dvRvtCcdList.size();i++){
            //TODO clone으로 바꾸기
            OpClmInfo target = OpClmInfo.builder().build();
            BeanUtils.copyProperties(this, target);

            //TODO 처리 순번을 받아서 +i하기
            target.setProcSeq(this.getProcSeq() + (i +1));
            target.setOrgProcSeq(this.getProcSeq());
            target.setOrdClmTpCd(opClmBase.getOpt0003Code());
            target.setOrdPrgsScd(opClmBase.getOrdPrgsScd());
            target.setDvRvtCcd(opClmBase.getOpt0013CodeList().get(i));

            opClmInfoBaseList.add(target);
        }

        return opClmInfoBaseList;
    }
}
