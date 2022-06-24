package com.plateer.ec1.promotion.vo.request;

import com.plateer.ec1.common.model.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PrmRequestBase {

    private String memberNo;
    private List<Product> productList;
    private String prmTypeCode;
}
