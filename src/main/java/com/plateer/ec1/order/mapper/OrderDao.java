package com.plateer.ec1.order.mapper;

import com.plateer.ec1.order.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {

    void insertOrderData(OrderVO orderVO);
}
