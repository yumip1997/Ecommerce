package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.define.OpBnfBase;
import com.plateer.ec1.claim.enums.define.OpClmInsertBase;
import com.plateer.ec1.claim.enums.define.OpClmUpdateBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
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

    public List<OpClmInfo> toInsertOpClmInfoList(OpClmInsertBase opClmInsertBase, String clmNo){
        List<OpClmInfo> opClmInfoBaseList = new ArrayList<>();

        for(int i = 0; i< opClmInsertBase.getDvRctCcdList().size(); i++){
            OpClmInfo target = new OpClmInfo();
            BeanUtils.copyProperties(this, target);

            target.setProcSeq(this.getProcSeq() + (i + 1));
            target.setOrgProcSeq(this.getProcSeq());
            target.setOrdClmTpCd(opClmInsertBase.getOrdClmTpCd().get(i));
            target.setOrdPrgsScd(opClmInsertBase.getOrdPrgsScd().get(i));
            target.setDvRvtCcd(opClmInsertBase.getDvRctCcdList().get(i));
            target.setOrdClmAcptDtime(LocalDateTime.now());
            target.setOrdClmReqDtime(LocalDateTime.now());
            target.setOrdClmCmtDtime(opClmInsertBase.getCmtDtimeSupplier().get());
            target.setDvGrpNo(opClmInsertBase.getDvpGrpOperator().applyAsInt(this.dvGrpNo));
            target.setClmNo(clmNo);

            opClmInfoBaseList.add(target);
        }

        return opClmInfoBaseList;
    }

    public List<OpOrdBnfRelInfo> toInsertOpOrdBnfRelInfoList(OpBnfBase opBnfBase, String clmNo){
        return Optional.ofNullable(this.getBenefitBaseVOList())
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toInsertOpOrdBnfRelInfo(opBnfBase, clmNo))
                .collect(Collectors.toList());
    }

    public List<CupIssVO> toCupIssVOList(String mbrNo){
        return Optional.ofNullable(this.getBenefitBaseVOList())
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toCupIssVO(mbrNo))
                .collect(Collectors.toList());
    }

    public List<OpClmInfo> toUpdateOpClmInfoList(OpClmUpdateBase updateBase, Supplier<ClaimGoodsInfo> supplier){
        List<OpClmInfo> updateList = new ArrayList<>();
        ClaimGoodsInfo orgOrd = isReturnWithdrawalReq() ? supplier.get() : this;

        ClaimGoodsInfo orgOrdUpdate = updateBase.getOrgOrdUpdate(orgOrd);
        ClaimGoodsInfo orgClmUpdate = updateBase.getOrgClmUpdate(this);

        if(orgOrdUpdate != this){
            updateList.add(convertOpClmInfo(orgOrdUpdate));
        }

        if(orgClmUpdate != this){
            updateList.add(convertOpClmInfo(orgClmUpdate));
        }

        return updateList;
    }

    public OpClmInfo convertOpClmInfo(ClaimGoodsInfo claimGoodsInfo){
        OpClmInfo target = new OpClmInfo();
        BeanUtils.copyProperties(claimGoodsInfo, target);
        return target;
    }

    private boolean isReturnWithdrawalReq(){
        return OPT0003Code.RETURN_ACCEPT.code.equals(this.ordClmTpCd) || OPT0003Code.EXCHANGE_ACCEPT.code.equals(this.ordClmTpCd);
    }

    public List<OpOrdBnfInfo> toUpdateOpOrdBnfInfoList(OpBnfBase opBnfBase){
        return Optional.ofNullable(this.getBenefitBaseVOList())
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toUpdateOpOrdBnfInfo(opBnfBase))
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
