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
public class OrderProductVO extends OrderProductBaseVO {

    private int ordCnt;
    private List<OrderBenefitBaseVO> productBenefits;

}
