package com.plateer.ec1.threadLocalTest;

import org.junit.jupiter.api.Test;

public class ClaimContext1Test {

    @Test
    public void threadTest(){
        PaymentApproveService paymentApproveService = new PaymentApproveService();
        ClaimContext1 claimContext1 = new ClaimContext1(paymentApproveService);

        for(int i=0;i<10;i++){
           Thread thread = new Thread(claimContext1::doClaim);
           thread.start();
        }
    }
}
