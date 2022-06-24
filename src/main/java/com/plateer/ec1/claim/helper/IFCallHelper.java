package com.plateer.ec1.claim.helper;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Log4j2
public class IFCallHelper {
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void callRestoreCouponIF(){
        log.info("쿠폰 복원 인터페이스를 호출한다.");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void callPaymentIF(){
        log.info("결제 인터페이스를 호출한다.");
    }

}
