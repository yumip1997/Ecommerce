package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClaimInsertBase {

    private List<OpClmInfo> opClmInfoList;

    private List<OpOrdCostInfo> opOrdCostInfoList;

    private List<OpOrdBnfRelInfo> opOrdBnfRelInfoList;

    public void setClmNo(String clmNo){
        for (OpClmInfo opClmInfo : opClmInfoList) {
            opClmInfo.setClmNo(clmNo);
        }

        for (OpOrdBnfRelInfo opOrdBnfRelInfo : opOrdBnfRelInfoList) {
            opOrdBnfRelInfo.setClmNo(clmNo);
        }

        for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
            opOrdCostInfo.setClmNo(clmNo);
        }
    }

}
