package com.plateer.ec1.product.service;

import com.plateer.ec1.product.vo.ProductInfoVO;

import java.util.List;

public interface ProductInfoService {

    List<ProductInfoVO> getProductInfoVo(List<ProductInfoVO> reqList);

}
