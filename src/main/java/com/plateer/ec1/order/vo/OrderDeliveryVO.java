package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.validation.groups.GeneralPrd;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeliveryVO {

    private int dvpSeq;
    @NotNull
    private String rmtiNm;
    @NotNull
    private String rmtiHpNo;
    @NotNull(groups = GeneralPrd.class)
    private String rmtiAddr;
    @NotNull(groups = GeneralPrd.class)
    private String rmtiAddrDtl;
    private List<OrderDeliveryGroupInfoVO> orderDeliveryGroupInfoVOList;

}
