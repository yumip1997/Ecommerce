package com.plateer.ec1.order.service;

import com.plateer.ec1.claim.helper.MonitoringLogHelper;
import com.plateer.ec1.order.vo.OrderDto;
import com.plateer.ec1.order.vo.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final MonitoringLogHelper monitoringLogHelper;

    public Long insertOrderHistory(OrderRequest orderRequest){
        return monitoringLogHelper.insertMonitoringLog(orderRequest.toString());
    }

    public void updateOrderHistory(OrderDto orderDto, Long historyNo){
        monitoringLogHelper.updateMonitoringLog(historyNo, orderDto.toString());
    }


}
