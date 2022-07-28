package com.plateer.ec1.order.vo;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderProductViewVO {

    private final OrderRequestVO orderRequestVO;
    private final List<ProductInfoVO> productInfoVOList;
    private Map<String, ProductInfoVO> productInfoVOMap;


    public OrderProductViewVO(OrderRequestVO orderRequestVO, List<ProductInfoVO> productInfoVOList) {
        this.orderRequestVO = orderRequestVO;
        this.productInfoVOList = productInfoVOList;
        this.productInfoVOMap = toProductInfoVOMap(productInfoVOList);
    }

    private Map<String, ProductInfoVO> toProductInfoVOMap(List<ProductInfoVO> productInfoVOList){
        return productInfoVOList.stream()
                .collect(Collectors.toMap(ProductInfoVO::getGoodNoItemNo, Function.identity()));
    }

}
