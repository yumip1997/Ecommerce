package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductVO extends OrderProductBaseVO {

    @Min(0)
    private int ordCnt;
    private long ordAmt;
    private List<OrderBenefitBaseVO> productBenefits;

    public OpClmInfo toOpClmInfo(String ordNo, int ordSeq, int dvGrpNo){
        return OpClmInfo.builder()
                .ordNo(ordNo)
                .ordSeq(ordSeq)
                .procSeq(1)
                .ordGoodsNo(this.getGoodsNo())
                .ordItemNo(this.getItemNo())
                .ordClmTpCd(OPT0003Code.ORDER.getCode())
                .dvRvtCcd(OPT0013Code.DELIVERY.getCode())
                .cnclCnt(0)
                .rtgsCnt(0)
                .dvGrpNo(dvGrpNo)
                .ordPrgsScd(OPT0004Code.ORDER_WAITING.getCode())
                .ordCnt(this.getOrdCnt())
                .ordAmt(this.getOrdAmt())
                .build();
    }
}
