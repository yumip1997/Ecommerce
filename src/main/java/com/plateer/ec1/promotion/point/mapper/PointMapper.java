package com.plateer.ec1.promotion.point.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointMapper {

    Long getPntBlcByMbrNo(String mbrNo);
}
