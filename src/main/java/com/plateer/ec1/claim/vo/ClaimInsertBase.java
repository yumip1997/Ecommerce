package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClaimInsertBase {

    List<OpClmInfo> opClmInfoList;

    List<OpOrdCostInfo> opOrdCostInfoList;

    OpOrdBnfRelInfo opOrdBnfRelInfo;

}
