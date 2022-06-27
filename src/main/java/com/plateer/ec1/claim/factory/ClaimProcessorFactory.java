package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import com.plateer.ec1.common.factory.FactoryTemplate;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class ClaimProcessorFactory extends FactoryTemplate<ProcessorType, ClaimProcessor>{

    public ClaimProcessorFactory(List<ClaimProcessor> list) {
        super(list);
    }
}
