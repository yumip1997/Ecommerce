package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpDvpInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeliveryGroupInfoVO {

    private int dvGrpNo;

    @NotEmpty
    @Valid
    private List<OrderCostInfo> orderCostInfoList;

    @NotEmpty
    @Valid
    private List<OrderProductBaseVO> orderProductBaseVOList;

    public OpDvpInfo toOpDvpInfo(String ordNo, int dvSeq, String dvMthdCd){
        return OpDvpInfo.builder()
                .dvpSeq(dvSeq)
                .dvGrpNo(this.dvGrpNo)
                .dvMthdCd(dvMthdCd)
                .ordNo(ordNo)
                .build();
    }

    public List<OpOrdCostInfo> toOpOrdCostInfoList(String ordNo){
        return this.getOrderCostInfoList().stream()
                .map(e -> e.toOpOrdCostInfo(ordNo, this.dvGrpNo))
                .collect(Collectors.toList());
    }

    public List<OpClmInfo> toOpClmInfoList(String ordNo, Map<String, OrderProductVO> map){
        return this.getOrderProductBaseVOList().stream()
                .map(e -> map.get(e.getGoodsNoItemNo()))
                .map(e -> e.toOpClmInfo(ordNo, this.dvGrpNo))
                .collect(Collectors.toList());
    }

}
