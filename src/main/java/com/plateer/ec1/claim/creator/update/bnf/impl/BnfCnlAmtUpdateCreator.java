package com.plateer.ec1.claim.creator.update.bnf.impl;

import com.plateer.ec1.claim.creator.update.bnf.OpBnfInfoUpdateCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;
import static java.util.stream.Collectors.*;

@Component
public class BnfCnlAmtUpdateCreator implements OpBnfInfoUpdateCreator {

    @Override
    public List<OpOrdBnfInfo> create(ClaimView claimView) {
        List<OpOrdBnfInfo> opOrdBnfInfoList = claimView.getOrderBenefitBaseVOList().stream()
                .map(OrderBenefitBaseVO::toOrdBnfInoOfReverseCnclBnfAmt)
                .collect(Collectors.toList());


        return sumOrdCnlBnfAmtByOrdBnfNo(opOrdBnfInfoList);
    }

    public List<OpOrdBnfInfo> sumOrdCnlBnfAmtByOrdBnfNo(List<OpOrdBnfInfo> opOrdBnfInfoList) {
        Map<String, List<OpOrdBnfInfo>> map = opOrdBnfInfoList.stream()
                .collect(groupingBy(OpOrdBnfInfo::getOrdBnfNo));

        Map<String, Long> mapOfSumCnclAmtByBnfNo = opOrdBnfInfoList.stream()
                .collect(groupingBy(OpOrdBnfInfo::getOrdBnfNo, summingLong(OpOrdBnfInfo::getOrdCnclBnfAmt)));

        return mapOfSumCnclAmtByBnfNo.entrySet().stream()
                .map(e -> OpOrdBnfInfo.builder()
                        .ordBnfNo(e.getKey())
                        .ordCnclBnfAmt(e.getValue())
                        .ordBnfAmt(map.get(e.getKey()).get(0).getOrdBnfAmt())
                        .cpnIssNo(map.get(e.getKey()).get(0).getCpnIssNo())
                        .build())
                .collect(toList());
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCA, GRA, GRW);
    }
}
