package com.plateer.ec1.threadLocalTest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.RecordApplicationEvents;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClaimContext1 {

    public static final ThreadLocal<ExternalRollback> externalRollbackThreadLocal = new ThreadLocal<>();
    private final PaymentApproveService paymentApproveService;

    public void doClaim(){
        try{
            paymentApproveService.doPay();
            int i = 1 / 0;
        }catch (Exception e){
            rollback();
        }finally {
            externalRollbackThreadLocal.remove();
        }
    }

    private void rollback(){
//        ExternalRollback externalRollback = externalRollbackThreadLocal.get();
        log.info("쓰레드 - {}", Thread.currentThread().getName());
        log.info("externalRollback - {}", externalRollbackThreadLocal.get().getName());
    }
}
