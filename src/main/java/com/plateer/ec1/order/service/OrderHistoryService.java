package com.plateer.ec1.order.service;

import com.plateer.ec1.claim.helper.MonitoringLogHelper;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.OrderRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final MonitoringLogHelper monitoringLogHelper;

    public Long insertOrderHistory(OrderRequestVO orderRequestVO){
        return monitoringLogHelper.insertMonitoringLog(orderRequestVO.toString());
    }

    public void updateOrderHistory(OrderVO orderVO, Long historyNo){
        monitoringLogHelper.updateMonitoringLog(historyNo, orderVO.toString());
    }


}
