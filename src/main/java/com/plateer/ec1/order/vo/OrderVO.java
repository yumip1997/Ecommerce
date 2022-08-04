package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.payment.vo.req.ApproveReqVO;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {

    private OpOrdBase opOrdBase;
    private List<OpGoodsInfo> opGoodsInfoList;
    private List<OpClmInfo> opClmInfoList;
    private List<OpDvpAreaInfo> opDvpAreaInfoList;
    private List<OpDvpInfo> opDvpInfoList;
    private List<OpOrdCostInfo> opOrdCostInfoList;
    private List<OpOrdBnfRelInfo> opOrdBnfRelInfoList;
    private List<OpOrdBnfInfo> opOrdBnfInfoList;

    private Map<String, OpClmInfo> opClmInfoMap;
    private Map<Long, OpOrdBnfInfo> opOrdBnfInfoMap;
    
    public ApproveReqVO toPayApproveReqVO(){
        return ApproveReqVO.builder().build();
    }

    public String getOrdNoFromOrdClmMap(String goodsNoItemNo){
        Map<String, OpClmInfo> ordClmMap = this.setUpOpClmInfoMap();
        OpClmInfo opClmInfo = ordClmMap.get(goodsNoItemNo);
        return opClmInfo.getOrdNo();
    }

    public int getOrdSeqFromOrdClmMap(String goodsNoItemNo){
        Map<String, OpClmInfo> ordClmMap = this.setUpOpClmInfoMap();
        OpClmInfo opClmInfo = ordClmMap.get(goodsNoItemNo);
        return opClmInfo.getOrdSeq();
    }

    private Map<String, OpClmInfo> setUpOpClmInfoMap(){
        if(this.opClmInfoMap == null){
            this.opClmInfoMap = toOpClmInfoMapByGoodsNoItemNo();
        }
        return this.opClmInfoMap;
    }

    private Map<String, OpClmInfo> toOpClmInfoMapByGoodsNoItemNo(){
        return this.getOpClmInfoList().stream()
                .collect(Collectors.toMap(OpClmInfo::getGoodsNoItemNo, Function.identity()));
    }

    public String getOrdBnfFromOrdBnfMap(Long cpnIssNo){
        Map<Long, OpOrdBnfInfo> ordBnfInfoMap = this.setUpOpOrdBnfInfoMap();
        OpOrdBnfInfo opOrdBnfInfo = ordBnfInfoMap.get(cpnIssNo);
        return opOrdBnfInfo.getOrdBnfNo();
    }

    private Map<Long, OpOrdBnfInfo> setUpOpOrdBnfInfoMap(){
        if(this.opOrdBnfInfoMap == null){
            this.opOrdBnfInfoMap = toOpOrdBnfInfoMapByCpnIssNo();
        }
        return this.opOrdBnfInfoMap;
    }

    private Map<Long, OpOrdBnfInfo> toOpOrdBnfInfoMapByCpnIssNo(){
        return this.getOpOrdBnfInfoList().stream()
                .collect(Collectors.toMap(OpOrdBnfInfo::getCpnIssNo, Function.identity()));
    }
}
