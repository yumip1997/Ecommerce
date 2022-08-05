package com.plateer.ec1.order.vo.req;

import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderBenefitVO;
import com.plateer.ec1.order.vo.OrderDeliveryVO;
import com.plateer.ec1.order.vo.OrderProductVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.payment.enums.OPT0009Code;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.req.ApproveReqVO;
import lombok.*;
import org.springframework.core.annotation.Order;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestVO extends OrderClaimBaseVO implements Cloneable{

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

    public List<OpOrdBnfInfo> toCartBnfInoList(Supplier<String> bnfNoSupplier){
        return Optional.ofNullable(this.getOrderBenefitVOList())
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toOpOrdBnfInfo(bnfNoSupplier.get()))
                .collect(Collectors.toList());
    }

    public boolean isContainsVacctPayment(){
        return payInfoVOList.stream()
                .anyMatch(payInfoVO -> OPT0009Code.VIRTUAL_ACCOUNT == payInfoVO.getMethodType());
    }

    public Map<String, OrderProductVO> getOrderProductVOMap(){
        if(this.orderProductVOMap == null){
            this.orderProductVOMap = makeOrderProductVOMap();
        }
        return this.orderProductVOMap;
    }

    private Map<String, OrderProductVO> makeOrderProductVOMap(){
        return this.getOrderProductVOList().stream()
                .collect(Collectors.toMap(OrderProductBaseVO::getGoodsNoItemNo, Function.identity()));
    }

    public OrderProductVO getOrderProductVOFromMap(String goodsNoItemNo){
        Map<String, OrderProductVO> map = this.getOrderProductVOMap();
        return map.get(goodsNoItemNo);
    }
    
    public long getTotalPrdBnfAplyOrdPrc(List<OrderProductBaseVO> orderProductBaseVOList){
        return Optional.ofNullable(orderProductBaseVOList)
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> this.getOrderProductVOFromMap(e.getGoodsNoItemNo()))
                .mapToLong(OrderProductVO::getPrdBnfAplyOrdPrc)
                .sum();
    }

    public ApproveReqVO toApproveReqVO(){
        return ApproveReqVO.builder()
                .orderInfoVO(this.toOrderInfoVO())
                .payInfoVOList(this.getPayInfoVOList())
                .build();
    }

    public OrderInfoVO toOrderInfoVO(){
        OrderProductVO orderProductVO = this.getOrderProductVOList().get(0);
        OrderBasicVO orderBasicVO = this.getOrderBasicVO();

        return OrderInfoVO.builder()
                .ordNo(this.getOrdNo())
                .goodName(orderProductVO.getGoodsNm())
                .buyerName(orderBasicVO.getOrdNm())
                .buyerEmail(orderBasicVO.getOrdEmail())
                .build();
    }

    @Override
    public OrderRequestVO clone() {
        try {
            return (OrderRequestVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
