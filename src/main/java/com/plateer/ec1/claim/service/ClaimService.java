package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.enums.ClaimDefine;
import com.plateer.ec1.claim.enums.ClaimProcessorType;
import com.plateer.ec1.claim.factory.ClaimProcessorFactory;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import com.plateer.ec1.claim.vo.ClaimDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClaimService {

    private final ClaimProcessorFactory claimProcessorFactory;

    public void claim(ClaimDto claimDto){

        try{
            ClaimProcessorType claimProcessorType = ClaimDefine.findClaimProcessorType(claimDto);
            ClaimProcessor claimProcessor = claimProcessorFactory.get(claimProcessorType);

            claimProcessor.doProcess(claimDto);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
