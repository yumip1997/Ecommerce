package com.plateer.ec1.order.vo;

import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductView {

    private OrderProductVO orderProductVO;
    private ProductInfoVO productInfoVO;

}
