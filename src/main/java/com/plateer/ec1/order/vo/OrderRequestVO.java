package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestVO extends OrderClaimBaseVO {

    @NotNull
    private String systemType;
    @NotNull
    private String orderType;

    @Valid
    @NotNull
    private OrderBasicVO orderBasicVO;

    @Valid
    @NotEmpty
    private List<OrderProductVO> orderProductVOList;

    @Valid
    @NotEmpty
    private List<OrderDeliveryVO> orderDeliveryVOList;

    private List<OrderBenefitVO> orderBenefitVOList;

    @Valid
    @NotEmpty
    private List<PayInfoVO> payInfoVOList;


}
