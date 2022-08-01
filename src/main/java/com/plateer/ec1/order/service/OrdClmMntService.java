package com.plateer.ec1.order.service;

import com.plateer.ec1.common.model.order.OpOrdClmMntLog;
import com.plateer.ec1.order.enums.OPT0012Code;
import com.plateer.ec1.order.mapper.OrdClmMntLogTrxMapper;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrdClmMntService {

    private final OrdClmMntLogTrxMapper ordClmMntLogTrxMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long insertOrderHistory(OrderClaimBaseVO orderClaimBaseVO) {
        OpOrdClmMntLog insertData = OpOrdClmMntLog.getInsertData(orderClaimBaseVO);
        return ordClmMntLogTrxMapper.insertMonitoringLog(insertData);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T, U> void updateOrderHistory(Long logSeq, OrdClmCreationVO<T,U> creationVO){
        OpOrdClmMntLog updateData = OpOrdClmMntLog.getUpdateData(logSeq, creationVO);
        ordClmMntLogTrxMapper.updateMonitoringLog(updateData);
    }
}
