package com.plateer.ec1.order.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeliveryVO {

    private int dvpSeq;
    @NotEmpty
    private String rmtiNm;
    @NotEmpty
    private String rmtiHpNo;
    private String rmtiAddr;
    private String rmtiAddrDtl;
    private List<OrderDeliveryGroupInfoVO> orderDeliveryGroupInfoVOList;

}
