package com.plateer.ec1.claim.processor.concrete;

import com.plateer.ec1.claim.enums.ProcessorType;
import com.plateer.ec1.claim.factory.ClaimValidatorFactory;
import com.plateer.ec1.claim.helper.ClaimDataManipulateHelper;
import com.plateer.ec1.claim.helper.MonitoringLogHelper;
import com.plateer.ec1.claim.processor.ClaimProcessor;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.claim.vo.LogVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AcceptWithdrawalProcessor extends ClaimProcessor {

    public AcceptWithdrawalProcessor(ClaimValidatorFactory claimValidatorFactory,
                                     MonitoringLogHelper monitoringLogHelper,
                                     ClaimDataManipulateHelper claimDataManipulateHelper) {
        super(claimValidatorFactory, monitoringLogHelper, claimDataManipulateHelper);
    }

    @Override
    public void doProcess(ClaimBaseVO claimBaseVO) {
        log.info("접수철회 프로세스를 진행한다");

        Long logKey = null;
        LogVO<ClaimInsertBase, ClaimUpdateBase> logVO = LogVO.<ClaimInsertBase, ClaimUpdateBase>builder().build();

        try{
            //클레임 채번
            setUpClaimNum(claimBaseVO);

            //모니터링 로그 insert
            logKey = monitoringLogHelper.insertMonitoringLog(claimBaseVO.toString());

            //validation check
            doValidationProcess(claimBaseVO);

            //데이터 생성 후 조작
            logVO = doClaimDataManipulationProcess(claimBaseVO);

            //금액검증
            verifyAmount(claimBaseVO);
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            monitoringLogHelper.updateMonitoringLog(logKey, logVO.toString());
        }
    }

    @Override
    public ProcessorType getType() {
        return ProcessorType.ACCEPT_WITHDRAWAL;
    }

}
