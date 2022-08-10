package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.manipulator.ClaimDataManipulator;
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

            validate(claimRequestVO, claimContextVO.getClaimDefine());

            creationVO = create(claimRequestVO);

            manipulateClaim(creationVO);

        }catch (Exception e){
            creationVO.setException(e);
            throw e;
        }finally {
            ordClmMntService.updateOrderHistory(logSeq, creationVO);
        }
    }

    private void setUpClaimNum(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine){

    }

    private void validate(ClaimRequestVO claimRequestVO, ClaimDefine claimDefine) {

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


}
