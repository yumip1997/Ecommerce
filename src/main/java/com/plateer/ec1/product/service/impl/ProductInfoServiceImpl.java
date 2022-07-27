package com.plateer.ec1.product.service.impl;

import com.plateer.ec1.product.mapper.ProductInfoMapper;
import com.plateer.ec1.product.service.ProductInfoService;
import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInfoServiceImpl implements ProductInfoService {

    private final ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfoVO> getProductInfoVo(List<ProductInfoVO> reqList) {
        return productInfoMapper.getProductInfoVo(reqList);
    }

}
