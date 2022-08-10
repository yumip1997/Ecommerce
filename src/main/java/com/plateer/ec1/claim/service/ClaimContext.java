package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.manipulator.ClaimDataManipulator;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import com.plateer.ec1.claim.strategy.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimContextVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.order.service.OrdClmMntService;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClaimContext {

    private final OrdClmMntService ordClmMntService;
    private final ClaimDataManipulator claimDataManipulator;

    @LogTrace
    @Transactional
    public void doClaim(ClaimRequestVO claimRequestVO, ClaimContextVO claimContextVO){
        Long logSeq = ordClmMntService.insertOrderHistory(claimRequestVO);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = new OrdClmCreationVO<>();

        try{
            setUpClaimNum(claimRequestVO, claimContextVO.getClaimDefine());

            validate(claimRequestVO, claimContextVO);

            creationVO = create(claimRequestVO);

            manipulateClaim(creationVO);

            doClaimAfterProcess(claimContextVO);

        }catch (Exception e){
            creationVO.setException(e);
            throw e;
        }finally {
            ordClmMntService.updateOrderHistory(logSeq, creationVO);
        }
    }

    private void setUpClaimNum(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){
        if(!claimDefine.isClaimNumFlag()) return;

    }

    private void validate(ClaimRequestVO claimRequestVO, ClaimContextVO claimContextVO) {
        ClaimValidator claimValidator = claimContextVO.getClaimValidator();
        ClaimDefine claimDefine = claimContextVO.getClaimDefine();

        claimValidator.isValid(claimRequestVO, claimDefine.getValidCode());
    }

    private OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> create(ClaimRequestVO claimRequestVO){
        return OrdClmCreationVO.<ClaimInsertBase, ClaimUpdateBase>builder()
                .insertData(ClaimDataCreator.createClaimInsertBase(claimRequestVO))
                .updateData(ClaimDataCreator.createClaimUpdateBase(claimRequestVO))
                .build();
    }

    private void manipulateClaim(OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO) {
        claimDataManipulator.insertClaimData(creationVO.getInsertData());
        claimDataManipulator.updateClaimData(creationVO.getUpdateData());
    }

    private void doClaimAfterProcess(ClaimContextVO claimContextVO) {
        ClaimAfterStrategy claimAfterStrategy = claimContextVO.getClaimAfterStrategy();
        if(claimAfterStrategy == null) return;

        claimAfterStrategy.call();
    }



}
