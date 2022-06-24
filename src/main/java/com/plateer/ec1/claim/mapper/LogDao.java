package com.plateer.ec1.claim.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogDao {
    Long insertMonitoringLog(String json);
    void updateMonitoringLog(String json);
}
