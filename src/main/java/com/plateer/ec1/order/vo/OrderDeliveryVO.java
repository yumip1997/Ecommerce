package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpDvpAreaInfo;
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
public class OrderDeliveryVO{

    private int dvpSeq;
    private String rmtiNm;
    private String rmtiHpNo;
    private String rmtiAddr;
    private String rmtiAddrDtl;
    @NotEmpty
    @Valid
    private List<OrderDeliveryGroupInfoVO> orderDeliveryGroupInfoVOList;

    public OpDvpAreaInfo toOpDvpAreaInfo(String ordNo){
        return OpDvpAreaInfo.builder()
                .ordNo(ordNo)
                .dvpSeq(this.getDvpSeq())
                .rmtiNm(this.getRmtiNm())
                .rmtiHpNo(this.getRmtiHpNo())
                .rmtiAddr(this.getRmtiAddr())
                .rmtiAddrDtl(this.getRmtiAddrDtl())
                .build();
    }

}
