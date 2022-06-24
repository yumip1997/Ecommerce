package com.plateer.ec1.promotion.vo.response;

import com.plateer.ec1.common.model.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//가격조정 응답 객체
//가격조정가에 셋팅된 product List를 담고 있음

@Getter
@Setter
public class PriceDiscountResponseVO extends ResponseBaseVO {

    private List<Product> productList;

}
