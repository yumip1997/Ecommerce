package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClaimAfterStrategyFactoryTest {

    @Autowired
    private ClaimAfterStrategyFactory claimAfterStrategyFactory;

    @Test
    void test(){
        ClaimAfterStrategy claimAfterStrategy = claimAfterStrategyFactory.get(ClaimStatusType.ACCEPT_WITHDRAWAL);
        Assertions.assertThat(claimAfterStrategy).isNull();
    }
}