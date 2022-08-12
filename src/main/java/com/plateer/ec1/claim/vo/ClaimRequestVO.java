package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.vo.OrderProductVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ClaimRequestVO extends OrderClaimBaseVO implements Cloneable{

    private String prdType;
    private String claimReqType;

    private List<ClaimBaseVO> claimBaseVOList;
    private List<OrderProductVO> orderProductVOList;

    public List<OpClmInfo> toInsertOpClmInfoList(ClaimDefine claimDefine, Supplier<String> clmNoSupplier){
        return this.getClaimBaseVOList()
                .stream()
                .map(e -> e.toOpClmInfoList(claimDefine.getOpClmBase(), clmNoSupplier))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public ClaimRequestVO clone() {
        try {
            return (ClaimRequestVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
