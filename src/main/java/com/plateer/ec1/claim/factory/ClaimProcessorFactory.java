package com.plateer.ec1.claim.factory;

import com.plateer.ec1.claim.enums.ClaimProcessorType;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ClaimProcessorFactory {

    private final Map<ClaimProcessorType, ClaimProcessor> map = new HashMap<>();

    public ClaimProcessorFactory(List<ClaimProcessor> claimProcessorList){
        claimProcessorList.forEach(claimProcessor -> map.put(claimProcessor.getType(), claimProcessor));
    }

    public ClaimProcessor getClaimProcessor(ClaimProcessorType claimProcessorType){
        return map.get(claimProcessorType);
    }
}
