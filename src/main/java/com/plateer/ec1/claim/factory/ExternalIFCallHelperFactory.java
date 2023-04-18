package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.externals.ExternalIFCallHelper;
import com.plateer.ec1.common.factory.AbstractFactoryMultiValue;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExternalIFCallHelperFactory extends AbstractFactoryMultiValue<ClaimBusiness, ExternalIFCallHelper> {

    public ExternalIFCallHelperFactory(List<ExternalIFCallHelper> list) {
        super(list);
    }

}
