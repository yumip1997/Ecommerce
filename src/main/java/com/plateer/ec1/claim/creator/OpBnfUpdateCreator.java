package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimException;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.common.excpetion.custom.DataCreationException;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.plateer.ec1.claim.enums.define.ClaimDefine.*;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public enum OpBnfUpdateCreator implements ClaimCreator<List<OpOrdBnfInfo>, List<OrderBenefitBaseVO>> {

    BNF_APPLY(e ->
            OpOrdBnfInfo.builder()
                    .ordBnfNo(e.getOrdBnfNo())
                    .ordCnclBnfAmt(e.getAplyAmt())
                    .build()
            , Collections.singletonList(GRW)),
    BNF_CANCEL(e ->
            OpOrdBnfInfo.builder()
                    .ordBnfNo(e.getOrdBnfNo())
                    .ordCnclBnfAmt(0)
                    .build()
            , Arrays.asList(GCC, MCA, GRA));

    private final Function<OrderBenefitBaseVO, OpOrdBnfInfo> bnfUpdateFunc;
    private final List<ClaimDefine> groups;

    @Override
    public List<ClaimDefine> groupingClaim() {
        return this.groups;
    }

    @Override
    public List<OpOrdBnfInfo> create(List<OrderBenefitBaseVO> orderBenefitBaseVOS) {
        List<OpOrdBnfInfo> OpOrdBnfInfoListPerOpOrdBnfNo = orderBenefitBaseVOS.stream()
                .map(this.bnfUpdateFunc)
                .collect(toList());

        return sumOrdCnlBnfAmtByOrdBnfNo(OpOrdBnfInfoListPerOpOrdBnfNo);
    }

    public List<OpOrdBnfInfo> sumOrdCnlBnfAmtByOrdBnfNo(List<OpOrdBnfInfo> opOrdBnfInfoList) {
        Map<String, Integer> mapOfSumCnclAmtByBnfNo = opOrdBnfInfoList.stream()
                        .collect(groupingBy(OpOrdBnfInfo::getOrdBnfNo, summingInt(OpOrdBnfInfo::getOrdCnclBnfAmt)));

        return mapOfSumCnclAmtByBnfNo.entrySet().stream()
                .map(e -> OpOrdBnfInfo.of(e.getKey(), e.getValue()))
                .collect(toList());
    }

    public static List<OpBnfUpdateCreator> getCreators(ClaimDefine claimDefine){
        return Arrays.stream(OpBnfUpdateCreator.values())
                .filter(e -> e.hasClaimDefine(claimDefine))
                .collect(toList());
    }
}
