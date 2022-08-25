package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

@RequiredArgsConstructor
public enum OpBnfRelInsertCreator implements ClaimCreator<List<OpOrdBnfRelInfo>, List<OrderBenefitBaseVO>> {

    BNF_REL_APPLY((e) -> {
        OpOrdBnfRelInfo opOrdBnfRelInfo = e.convertOpOrdBnfRelInfo();
        opOrdBnfRelInfo.setProcSeq(e.getProcSeq() + 1);
        opOrdBnfRelInfo.setAplyCnclCcd(OPT0005Code.APPLY.code);
        return opOrdBnfRelInfo;
    }, Collections.singletonList(GRW)),
    BNF_REL_CANCEL((e) -> {
        OpOrdBnfRelInfo opOrdBnfRelInfo = e.convertOpOrdBnfRelInfo();
        opOrdBnfRelInfo.setProcSeq(e.getProcSeq() + 1);
        opOrdBnfRelInfo.setAplyCnclCcd(OPT0005Code.APPLY.code);
        return opOrdBnfRelInfo;
    }, Arrays.asList(GCC, MCA, GRA));

    private final Function<OrderBenefitBaseVO, OpOrdBnfRelInfo> bnfRelInsertFunc;
    private final List<ClaimBusiness> groups;

    @Override
    public List<ClaimBusiness> getTypes() {
        return this.groups;
    }

    @Override
    public  List<OpOrdBnfRelInfo> create( List<OrderBenefitBaseVO> orderBenefitBaseVOS) {
        return orderBenefitBaseVOS.stream()
                .map(this.bnfRelInsertFunc)
                .collect(Collectors.toList());
    }

    public static List<OpBnfRelInsertCreator> getCreators(ClaimBusiness claimBusiness){
        return Arrays.stream(OpBnfRelInsertCreator.values())
                .filter(e -> e.hasClaimDefine(claimBusiness))
                .collect(Collectors.toList());
    }
}
