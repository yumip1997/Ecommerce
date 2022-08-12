package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.OpClmBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

    public List<OpClmInfo> toOpClmInfoList(OpClmBase opClmBase, Supplier<String> clmNoSupplier){
        List<OpClmInfo> opClmInfoBaseList = new ArrayList<>();

        for(int i=0;i<opClmBase.getOpt0013CodeList().size();i++){
            OpClmInfo target = new OpClmInfo();
            BeanUtils.copyProperties(this, target);

            target.setProcSeq(this.getProcSeq() + (i + 1));
            target.setOrgProcSeq(this.getProcSeq());
            target.setOrdClmTpCd(opClmBase.getOpt0003Code());
            target.setOrdPrgsScd(opClmBase.getOrdPrgsScd());
            target.setDvRvtCcd(opClmBase.getOpt0013CodeList().get(i));
            target.setClmNo(opClmBase.isCreateClaimNoFlag() ? clmNoSupplier.get() : this.getClmNo());
            target.setOrdClmCmtDtime(opClmBase.getClaimStatusType() == ClaimStatusType.COMPLETE ? LocalDateTime.now() : null);

            opClmInfoBaseList.add(target);
        }

        return opClmInfoBaseList;
    }
}
