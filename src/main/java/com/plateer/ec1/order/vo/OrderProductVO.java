package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.enums.OPT0013Code;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductVO extends OrderProductBaseVO {

    private List<OrderBenefitBaseVO> productBenefits;

    public OpClmInfo toOpClmInfo(String ordNo, int dvGrpNo){
        return OpClmInfo.builder()
                .ordNo(ordNo)
                .procSeq(1)
                .ordGoodsNo(this.getGoodsNo())
                .ordItemNo(this.getItemNo())
                .ordClmTpCd(OPT0003Code.ORDER.getCode())
                .dvRvtCcd(OPT0013Code.DELIVERY.getCode())
                .cnclCnt(0)
                .rtgsCnt(0)
                .dvGrpNo(dvGrpNo)
                .ordCnt(this.getOrdCnt())
                .ordAmt(this.getOrdAmt())
                .ordClmAcptDtime(LocalDateTime.now())
                .ordClmReqDtime(LocalDateTime.now())
                .build();
    }

    /*
    쿠폰적용된 금액
     */
    public long getPrdBnfAplyOrdPrc(){
        return this.getOrdPrcWithOrdCnt() - this.sumPrdBnf();
    }

    private long sumPrdBnf(){
        return Optional.ofNullable(this.getProductBenefits())
                .orElse(Collections.emptyList())
                .stream()
                .mapToLong(OrderBenefitBaseVO::getAplyAmt)
                .sum();
    }

    public List<OpOrdBnfInfo> toPrdBnfInoList(Supplier<String> bnfNoSupplier){
        return Optional.ofNullable(this.getProductBenefits())
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toOpOrdBnfInfo(bnfNoSupplier.get()))
                .collect(Collectors.toList());
    }
}
