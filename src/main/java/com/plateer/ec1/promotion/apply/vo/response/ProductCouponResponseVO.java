package com.plateer.ec1.promotion.apply.vo.response;

import com.plateer.ec1.promotion.apply.vo.ApplicablePdCupVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCouponResponseVO extends ResponseBaseVO {

    private List<ApplicablePdCupVO> applicablePdCupVOList;

    @Builder
    public ProductCouponResponseVO(String mbrNo, List<ApplicablePdCupVO> applicablePdCupVOList){
        super(mbrNo);
        this.applicablePdCupVOList = applicablePdCupVOList;
    }

}
