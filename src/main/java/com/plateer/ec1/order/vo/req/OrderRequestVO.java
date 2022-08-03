package com.plateer.ec1.order.vo.req;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderBenefitVO;
import com.plateer.ec1.order.vo.OrderDeliveryVO;
import com.plateer.ec1.order.vo.OrderProductVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.payment.enums.OPT0009Code;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Valid
    @NotEmpty
    private List<OrderDeliveryVO> orderDeliveryVOList;

    private List<OrderBenefitVO> orderBenefitVOList;

    @Valid
    @NotEmpty
    private List<PayInfoVO> payInfoVOList;

    private Map<String, OrderProductVO> orderProductVOMap;

    public boolean isContainsVacctPayment(){
        return payInfoVOList.stream()
                .anyMatch(payInfoVO -> OPT0009Code.VIRTUAL_ACCOUNT == payInfoVO.getMethodType());
    }

    public Map<String, OrderProductVO> setUpOrderProductVOMap(){
        if(this.orderProductVOMap == null){
            this.orderProductVOMap = makeOrderProductVOMap();
        }
        return this.orderProductVOMap;
    }

    private Map<String, OrderProductVO> makeOrderProductVOMap(){
        return this.getOrderProductVOList().stream()
                .collect(Collectors.toMap(OrderProductBaseVO::getGoodsNoItemNo, Function.identity()));
    }

}
