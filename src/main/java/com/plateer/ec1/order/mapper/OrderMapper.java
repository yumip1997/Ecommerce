package com.plateer.ec1.order.mapper;

import com.plateer.ec1.order.vo.OrderProductVO;
import com.plateer.ec1.order.vo.OrderProductView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    List<OrderProductView> getOrderProductView(List<OrderProductVO> orderProductVOList);

}
