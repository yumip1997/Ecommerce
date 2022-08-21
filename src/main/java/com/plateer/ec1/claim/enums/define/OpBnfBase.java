package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;

@RequiredArgsConstructor
public enum OpBnfBase {

    BNF_APPLY((e, clmNo) ->{
        return OpOrdBnfRelInfo.builder()
                .ordNo(e.getOrdNo())
                .ordBnfNo(e.getBnfNo())
                .ordSeq(e.getOrdSeq())
                .procSeq(e.getProcSeq() + 1)
                .aplyCnclCcd(OPT0005Code.APPLY.code)
                .aplyAmt(e.getAplyAmt())
                .clmNo(clmNo)
                .build();
    }, (e) -> {
        return OpOrdBnfInfo.builder()
                .ordBnfNo(e.getBnfNo())
                .ordCnclBnfAmt(e.getAplyAmt())
                .build();
    }),
    BNF_CANCEL((e, clmNo) ->{
        return OpOrdBnfRelInfo.builder()
                .ordNo(e.getOrdNo())
                .ordBnfNo(e.getBnfNo())
                .ordSeq(e.getOrdSeq())
                .procSeq(e.getProcSeq() + 1)
                .aplyCnclCcd(OPT0005Code.CANCEL.code)
                .aplyAmt(e.getAplyAmt())
                .clmNo(clmNo)
                .build();
    }, (e) -> {
        return OpOrdBnfInfo.builder()
                .ordBnfNo(e.getBnfNo())
                .ordCnclBnfAmt(0)
                .build();
    });

    private final BiFunction<OrderBenefitBaseVO, String, OpOrdBnfRelInfo> opOrdBnfRelInfoFunction;
    private final Function<OrderBenefitBaseVO, OpOrdBnfInfo> opOrdBnfInfoFunction;

    public OpOrdBnfRelInfo getOpOrdBnfRelInfo(OrderBenefitBaseVO baseVO, String clmNo){
        return this.opOrdBnfRelInfoFunction.apply(baseVO, clmNo);
    }

    public OpOrdBnfInfo getOpOrdBnfInfo(OrderBenefitBaseVO baseVO){
        return this.opOrdBnfInfoFunction.apply(baseVO);
    }

}
