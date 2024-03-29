package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.enums.OPT0013Code;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
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

    @Min(1)
    private int ordCnt;
    private long salePrc;
    private long prmPrc;
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
                .ordAmt(this.getOrdPrc())
                .ordClmAcptDtime(LocalDateTime.now())
                .ordClmReqDtime(LocalDateTime.now())
                .build();
    }

    public long getOrdPrc(){
        return this.prmPrc == 0 ? this.salePrc : this.prmPrc;
    }

    public long getPrdBnfAplyOrdPrc(){
        return this.getOrdPrcWithOrdCnt() - this.sumPrdBnf();
    }

    public long getOrdPrcWithOrdCnt(){
        return this.getOrdPrc() * this.ordCnt;
    }

    public long sumPrdBnf(){
        return Optional.ofNullable(this.getProductBenefits())
                .orElse(Collections.emptyList())
                .stream()
                .mapToLong(OrderBenefitBaseVO::getAplyAmt)
                .sum();
    }

    public List<OpOrdBnfInfo> toPrdBnfInoList(Supplier<String> bnfNoSupplier){
        return this.geOrderBenefitBaseVO()
                .stream()
                .map(e -> e.toOpOrdBnfInfo(bnfNoSupplier.get()))
                .collect(Collectors.toList());
    }

    public List<OrderBenefitBaseVO> geOrderBenefitBaseVO(){
        return Optional.ofNullable(this.getProductBenefits())
                .orElseGet(Collections::emptyList);
    }

}
