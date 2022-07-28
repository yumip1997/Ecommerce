package com.plateer.ec1.order.vo;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductViewVO {

    private OrderRequestVO orderRequestVO;
    private List<ProductInfoVO> productInfoVOList;
    private Map<String, ProductInfoVO> productInfoVOMap;

}
