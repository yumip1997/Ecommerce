package com.plateer.ec1.promotion.apply.vo;

import com.plateer.ec1.common.model.product.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrmAplyVO {

    private String goodsItemNo;
    private Product product;
    private List<ApplicableCupVO> applicableCupVOList;

}
