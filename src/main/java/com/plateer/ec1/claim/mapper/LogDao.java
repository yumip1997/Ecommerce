package com.plateer.ec1.claim.mapper;

import org.apache.ibatis.annotations.Mapper;

//TODO 삭제예정
@Mapper
public interface LogDao {
    Long insertMonitoringLog(String json);
    void updateMonitoringLog(String json);
}
