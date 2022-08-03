package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpDvpInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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
    private List<OrderProductVO> orderProductVOList;

    public OpDvpInfo toOpDvpInfo(String ordNo, int dvSeq, String dvMthdCd){
        return OpDvpInfo.builder()
                .dvpSeq(dvSeq)
                .dvGrpNo(this.dvGrpNo)
                .dvMthdCd(dvMthdCd)
                .ordNo(ordNo)
                .build();
    }

}
