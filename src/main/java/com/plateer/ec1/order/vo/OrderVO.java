package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.payment.vo.req.ApproveReqVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends OrderClaimBaseVO {

    private OpOrdBase opOrdBase;
    private List<OpGoodsInfo> opGoodsInfoList;
    private List<OpClmInfo> opClmInfoList;
    private List<OpDvpAreaInfo> opDvpAreaInfoList;
    private List<OpDvpInfo> opDvpInfoList;
    private List<OpOrdCostInfo> opOrdCostInfoList;
    private List<OpOrdBnfRelInfo> opOrdBnfRelInfoList;
    private List<OpOrdBnfInfo> opOrdBnfInfoList;

    public ApproveReqVO toPayApproveReqVO(){
        return ApproveReqVO.builder().build();
    }
}
