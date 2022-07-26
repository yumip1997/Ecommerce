package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestVO extends OrderClaimBaseVO {

    private String systemType;
    private String orderType;

    private OrderBasicVO orderBasicVO;
    private List<OrderProductVO> orderProductVOList;
    private List<OrderDeliveryVO> orderDeliveryVOList;
    private List<OrderBenefitVO> orderBenefitVOList;
    private List<PayInfoVO> payInfoVO;

}
