package com.plateer.ec1.claim.helper;

import com.plateer.ec1.claim.mapper.LogDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Log4j2
public class MonitoringLogHelper {

    private final LogDao logDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long insertMonitoringLog(String json) {
        log.info("주문모니터링 로그 insert를 진행한다.");
        return 0L;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateMonitoringLog(Long logKey, String json) {
        log.info("주문모니터링 로그 update를 진행한다.");
    }

}
