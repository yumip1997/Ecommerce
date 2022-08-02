package com.plateer.ec1.order.vo.req;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderBenefitVO;
import com.plateer.ec1.order.vo.OrderDeliveryVO;
import com.plateer.ec1.order.vo.OrderProductVO;
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

    @Valid
    @NotNull
    private OrderBasicVO orderBasicVO;

    @Valid
    @NotEmpty
    private List<OrderProductVO> orderProductVOList;

    private List<OpClmInfo> orderClmList;

    @Valid
    @NotEmpty
    private List<OrderDeliveryVO> orderDeliveryVOList;

    private List<OrderBenefitVO> orderBenefitVOList;

    @Valid
    @NotEmpty
    private List<PayInfoVO> payInfoVOList;

}
