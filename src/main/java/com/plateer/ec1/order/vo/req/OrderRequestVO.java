package com.plateer.ec1.order.vo.req;

import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderBenefitVO;
import com.plateer.ec1.order.vo.OrderDeliveryVO;
import com.plateer.ec1.order.vo.OrderProductVO;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.payment.enums.OPT0009Code;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.req.ApproveReqVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.*;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;
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
        return this.getCartBenefitVO().stream()
                .map(e -> e.toOpOrdBnfInfo(bnfNoSupplier.get()))
                .collect(Collectors.toList());
    }

    private List<OrderBenefitVO> getCartBenefitVO(){
        return Optional.ofNullable(this.getOrderBenefitVOList())
                .orElseGet(Collections::emptyList);
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
                .mbrNo(orderBasicVO.getMbrNo())
                .ordNo(this.getOrdNo())
                .goodName(orderProductVO.getGoodsNm())
                .buyerName(orderBasicVO.getOrdNm())
                .buyerEmail(orderBasicVO.getOrdEmail())
                .build();
    }

    public List<CupIssVO> toCupIssVOList(){
        List<CupIssVO> cupIssVOList = new ArrayList<>();
        cupIssVOList.addAll(getProductCouponList());
        cupIssVOList.addAll(getCartCouponList());
        return cupIssVOList;
    }

    private List<CupIssVO> getProductCouponList(){
        return this.getOrderProductVOList().stream()
                .map(OrderProductVO::geOrderBenefitBaseVO)
                .flatMap(List::stream)
                .map(e -> e.toCupIssVO(this.getOrdNo(), this.getMbrNo()))
                .collect(Collectors.toList());
    }

    private List<CupIssVO> getCartCouponList(){
        return this.getCartBenefitVO().stream()
                .map(e -> e.toCupIssVO(this.getOrdNo(), this.getMbrNo()))
                .collect(Collectors.toList());
    }

    public String getMbrNo(){
        OrderBasicVO orderBasicVO = this.getOrderBasicVO();
        return orderBasicVO.getMbrNo();
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
