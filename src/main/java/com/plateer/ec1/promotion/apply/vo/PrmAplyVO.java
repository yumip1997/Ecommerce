package com.plateer.ec1.promotion.apply.vo;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrmAplyVO {

    private ProductInfoVO productInfoVO;
    private List<ApplicableCupVO> applicableCupVOList;

}
