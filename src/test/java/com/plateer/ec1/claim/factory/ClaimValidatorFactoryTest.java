package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ClaimValidatorFactoryTest {

    @Autowired
    ClaimValidatorFactory claimValidatorFactory;
    @Test
    void test_GCC(){
        List<ClaimValidator> values = claimValidatorFactory.getValues(ClaimBusiness.GCC);
    }
}