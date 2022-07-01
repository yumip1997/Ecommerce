package com.plateer.ec1.promotion.apply.vo;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PdPrmVO {

    private String goodsItemNo;
    private String prmCupIssNo;
    private ProductInfoVO productInfoVO;
    private ApplicablePrmVO applicablePrmVO;


}
