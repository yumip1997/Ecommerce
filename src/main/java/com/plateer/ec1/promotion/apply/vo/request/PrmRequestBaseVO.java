package com.plateer.ec1.promotion.apply.vo.request;

import com.plateer.ec1.common.model.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PrmRequestBaseVO {

    private String memberNo;
    private List<Product> productList;
    private String prmTypeCode;
}
