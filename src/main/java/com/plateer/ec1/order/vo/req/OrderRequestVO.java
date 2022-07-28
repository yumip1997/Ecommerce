package com.plateer.ec1.order.vo.req;

import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderBenefitVO;
import com.plateer.ec1.order.vo.OrderDeliveryVO;
import com.plateer.ec1.order.vo.OrderProductVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ProductInfoVO> toProductInfoVOList(){
        return orderProductVOList.stream()
                .map(OrderProductBaseVO::toProductInfoVO)
                .collect(Collectors.toList());
    }

}
