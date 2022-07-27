package com.plateer.ec1.product.mapper;

import com.plateer.ec1.product.vo.ProductInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoMapper {

    List<ProductInfoVO> getProductInfoVo(List<ProductInfoVO> reqList);

}
