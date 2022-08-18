package com.plateer.ec1.promotion.point.mapper;

import com.plateer.ec1.common.model.promotion.CcMbrPntModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PointTrxMapper {
    void insertPoint(CcMbrPntModel ccMbrPntModel);
}
