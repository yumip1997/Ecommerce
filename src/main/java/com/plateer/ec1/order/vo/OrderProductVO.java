package com.plateer.ec1.order.vo;

import com.plateer.ec1.claim.enums.DvRvtCcd;
import com.plateer.ec1.claim.enums.OrdClmTpCd;
import com.plateer.ec1.claim.enums.OrdPrgsScd;
import com.plateer.ec1.common.model.order.OpClmInfo;
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
                .ordClmTpCd(OrdClmTpCd.ORDER.getCode())
                .dvRvtCcd(DvRvtCcd.DELIVERY.getCode())
                .cnclCnt(0)
                .rtgsCnt(0)
                .dvGrpNo(dvGrpNo)
                .ordPrgsScd(OrdPrgsScd.ORDER_WAITING.getCode())
                .ordCnt(this.getOrdCnt())
                .ordAmt(this.getOrdAmt())
                .build();
    }
}
