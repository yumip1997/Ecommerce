package com.plateer.ec1.threadLocalTest;

public class PaymentApproveService {

    public void doPay(){
        ExternalRollback externalRollback = new ExternalRollback();
        externalRollback.setName(Thread.currentThread().getName());
        ClaimContext1.externalRollbackThreadLocal.set(externalRollback);
    }
}
