package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.factory.ClaimProcessorFactory;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ClaimService {

    private final ClaimProcessorFactory claimProcessorFactory;

    public void claim(ClaimBaseVO claimBaseVO){

        try{
            ProcessorType processorType = ClaimDefine.findClaimProcessorType(claimBaseVO);
            ClaimProcessor claimProcessor = claimProcessorFactory.get(processorType);

            claimProcessor.doProcess(claimBaseVO);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }
}
