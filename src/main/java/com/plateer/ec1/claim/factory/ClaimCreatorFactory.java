package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.creator.ClaimCreator;
import com.plateer.ec1.claim.creator.OpClmInsertCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.common.factory.MultiValueFactoryTemplate;

import java.util.Arrays;

public class ClaimCreatorFactory{

    static class OpClmInsertCreatorFactory extends MultiValueFactoryTemplate<ClaimBusiness, ClaimCreator<?,?>>{
        public OpClmInsertCreatorFactory() {
            super(Arrays.asList(OpClmInsertCreator.values()));
        }
    }

}
