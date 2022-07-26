package com.plateer.ec1.order.service;

import com.plateer.ec1.order.mapper.OrdClmMntLogTrxMapper;
import com.plateer.ec1.order.vo.OrdClmMntLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final OrdClmMntLogTrxMapper ordClmMntLogTrxMapper;

    public Long insertOrderHistory(OrdClmMntLogVO<?, ?> logVO) {
        return ordClmMntLogTrxMapper.insertMonitoringLog(logVO.toModel());
    }

    public void updateOrderHistory(OrdClmMntLogVO<?, ?> logVO){
        ordClmMntLogTrxMapper.updateMonitoringLog(logVO.toModel());
    }
}
