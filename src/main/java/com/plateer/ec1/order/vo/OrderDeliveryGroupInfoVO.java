package com.plateer.ec1.order.vo;

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


}
