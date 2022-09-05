package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimUpdateBase {

    private List<OpClmInfo> opClmInfoList;

    private List<OpOrdCostInfo> opOrdCostInfoList;

    private List<OpOrdBnfInfo> opOrdBnfInfoList;

    public List<Long> getRestoreCpnIssNoList(){
        return this.getOpOrdBnfInfoList().stream()
                .filter(e -> e.getOrdCnclBnfAmt() == e.getOrdBnfAmt())
                .map(OpOrdBnfInfo::getCpnIssNo)
                .collect(Collectors.toList());
    }

}
