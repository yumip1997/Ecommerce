package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.define.OpBnfBase;
import com.plateer.ec1.claim.enums.define.OpClmInsertBase;
import com.plateer.ec1.claim.enums.define.OpClmUpdateBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimRequestVO extends OrderClaimBaseVO implements Cloneable{

    @NotNull
    private String prdType;
    private List<String> ordClmReqTypes;
    @NotNull
    private String ordPrgsType;
    @NotNull
    private Long cnclReqAmt;
    @NotNull
    private String mbrNo;
    @NotEmpty
    private List<@Valid ClaimGoodsInfo> claimGoodsInfoList;

    private List<ClaimDeliveryInfo> claimDeliveryInfoList;

    public List<OpClmInfo> toInsertOpClmInfoList(OpClmInsertBase opClmInsertBase, String clmNo){
        if(opClmInsertBase == null) return Collections.emptyList();

        return claimGoodsInfoList.stream()
                .map(e -> e.toInsertOpClmInfoList(opClmInsertBase, clmNo))
                .flatMap(List::stream)
                .collect(toList());
    }

    public List<OpOrdBnfRelInfo> toInsertOpOrdBnfRelInfoList(OpBnfBase opBnfBase, String clmNo){
        if(opBnfBase == null) return Collections.emptyList();

        return claimGoodsInfoList.stream()
                .map(e -> e.toInsertOpOrdBnfRelInfoList(opBnfBase, clmNo))
                .flatMap(List::stream)
                .collect(toList());
    }

    public List<OpOrdCostInfo> toInsertOpOrdCostInfoList(String clmNo){
        return Optional.ofNullable(claimDeliveryInfoList)
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toOpOrdCostInfo(clmNo))
                .collect(toList());
    }

    public List<OpClmInfo> toUpdateOpClmInfoList(OpClmUpdateBase updateBase, Supplier<ClaimGoodsInfo> supplier){
        return claimGoodsInfoList.stream()
                .map(e -> e.toUpdateOpClmInfoList(updateBase, supplier))
                .flatMap(List::stream)
                .collect(toList());
    }

    public List<OpOrdBnfInfo> toUpdateOpOrdBnfInfo(OpBnfBase opBnfBase){
        if(opBnfBase == null) return Collections.emptyList();

        Map<String, Integer> mapOfSumCnclAmtByBnfNo = claimGoodsInfoList.stream()
                .map(e -> e.toUpdateOpOrdBnfInfoList(opBnfBase))
                .flatMap(List::stream)
                .collect(groupingBy(OpOrdBnfInfo::getOrdBnfNo, summingInt(OpOrdBnfInfo::getOrdCnclBnfAmt)));

        return mapOfSumCnclAmtByBnfNo.entrySet().stream()
                .map(e -> OpOrdBnfInfo.of(e.getKey(), e.getValue()))
                .collect(toList());
    }

    public List<OpOrdCostInfo> toUpdateOpOrdCostInfoList(){
        List<OpOrdCostInfo> updateList = isWithdrawalReq() ? toCostInfoApplyToCancel() : Collections.emptyList();
        return updateList;
    }

    public List<OpOrdCostInfo> toCostInfoApplyToCancel(){
        return this.claimDeliveryInfoList.stream()
                .filter(ClaimDeliveryInfo::isApply)
                .map(ClaimDeliveryInfo::toUpdateOpOrdCostInfo)
                .collect(toList());
    }

    public List<CupIssVO> toCupIssVOList(){
        return claimGoodsInfoList.stream()
                .map(e -> e.toCupIssVOList(this.mbrNo))
                .flatMap(List::stream)
                .collect(toList());
    }

    public boolean isWithdrawalReq(){
        return ClaimDefine.of(this).isWithrawalReq();
    }

    @Override
    public ClaimRequestVO clone() {
        try {
            return (ClaimRequestVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
