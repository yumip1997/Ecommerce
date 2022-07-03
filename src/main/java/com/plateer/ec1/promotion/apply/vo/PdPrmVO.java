package com.plateer.ec1.promotion.apply.vo;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
public class PdPrmVO {

    private String prmCupIssNo;
    private String goodsItemNo;

    private ProductInfoVO productInfoVO;
    private ApplicablePrmVO applicablePrmVO;


}
