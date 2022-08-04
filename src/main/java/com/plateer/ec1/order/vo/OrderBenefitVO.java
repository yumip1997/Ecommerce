package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBenefitVO extends OrderBenefitBaseVO {

    List<OrderProductBaseVO> orderProductBaseVOList;

    public long distributeAplyAmt(long prdBnfAplyOrdPrc, long totalPrdBnfApyOrdPrc){
        return (prdBnfAplyOrdPrc / totalPrdBnfApyOrdPrc) * this.getAplyAmt();
    }
}
