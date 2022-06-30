package com.plateer.ec1.promotion.apply.vo.response;

import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductCouponResponseVO extends ResponseBaseVO {

    private List<PrmAplyVO> prmAplyVOList;

    @Builder
    public ProductCouponResponseVO(String mbrNo, List<PrmAplyVO> prmAplyVOList){
        super(mbrNo);
        this.prmAplyVOList = prmAplyVOList;
    }

}
