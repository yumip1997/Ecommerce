package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;
import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
public enum OpBnfUpdateCreator implements ClaimCreator<List<OpOrdBnfInfo>, List<OrderBenefitBaseVO>> {

    BNF_APPLY(OrderBenefitBaseVO::toOrdBnfInoOfReverseCnclBnfAmt
            , Collections.singletonList(GRW)),
    BNF_CANCEL(OrderBenefitBaseVO::toOrdBnfInoOfReverseCnclBnfAmt
            , Arrays.asList(GCC, MCA, GRA));

    private final Function<OrderBenefitBaseVO, OpOrdBnfInfo> bnfUpdateFunc;
    private final List<ClaimBusiness> groups;

    @Override
    public List<ClaimBusiness> getTypes() {
        return this.groups;
    }

    @Override
    public List<OpOrdBnfInfo> create(List<OrderBenefitBaseVO> orderBenefitBaseVOS) {
        if(CollectionUtils.isEmpty(orderBenefitBaseVOS)) return Collections.emptyList();

        List<OpOrdBnfInfo> OpOrdBnfInfoListPerOpOrdBnfNo = orderBenefitBaseVOS.stream()
                .map(this.bnfUpdateFunc)
                .collect(toList());

        return sumOrdCnlBnfAmtByOrdBnfNo(OpOrdBnfInfoListPerOpOrdBnfNo);
    }

    public List<OpOrdBnfInfo> sumOrdCnlBnfAmtByOrdBnfNo(List<OpOrdBnfInfo> opOrdBnfInfoList) {
        Map<String, Long> mapOfSumCnclAmtByBnfNo = opOrdBnfInfoList.stream()
                .collect(groupingBy(OpOrdBnfInfo::getOrdBnfNo, summingLong(OpOrdBnfInfo::getOrdCnclBnfAmt)));

        return mapOfSumCnclAmtByBnfNo.entrySet().stream()
                .map(e -> OpOrdBnfInfo.of(e.getKey(), e.getValue()))
                .collect(toList());
    }

    public static List<OpBnfUpdateCreator> getCreators(ClaimBusiness claimBusiness) {
        return Arrays.stream(OpBnfUpdateCreator.values())
                .filter(e -> e.hasClaimDefine(claimBusiness))
                .collect(toList());
    }
}
