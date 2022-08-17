package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.manipulator.ClaimDataManipulator;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import com.plateer.ec1.claim.strategy.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.*;
import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.order.service.OrdClmMntService;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClaimContext {

    private final ClaimMapper claimMapper;
    private final OrdClmMntService ordClmMntService;
    private final ClaimDataManipulator claimDataManipulator;

    @LogTrace
    @Transactional
    public void doClaim(ClaimRequestVO claimRequestVO, ClaimContextVO claimContextVO){
        Long logSeq = ordClmMntService.insertOrderHistory(claimRequestVO);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = new OrdClmCreationVO<>();

        try{
            validate(claimRequestVO.getClaimGoodsInfoList(), claimContextVO);

            creationVO = ClaimDataCreator.createOrdClmCreationVO(claimRequestVO, claimMapper::getClaimNo);

            claimDataManipulator.manipulateClaimData(creationVO.getInsertData(), creationVO.getUpdateData());

            doClaimAfterProcess(claimRequestVO,claimContextVO);
        }catch (Exception e){
            creationVO.setException(e);
            throw e;
        }finally {
            ordClmMntService.updateOrderHistory(logSeq, creationVO);
        }
    }

    private void validate(List<ClaimGoodsInfo> claimGoodsInfoList , ClaimContextVO claimContextVO) {
        List<ClaimView> claimViewList = claimMapper.getClaimViewList(claimGoodsInfoList);

        ClaimValidator claimValidator = claimContextVO.getClaimValidator();
        ClaimDefine claimDefine = claimContextVO.getClaimDefine();

        claimValidator.isValidOrdPrgsScd(claimViewList, claimDefine.getValidOrdPrgsStrList());
        claimValidator.isValidProductType(claimViewList,claimDefine.getPrdTypeStr());
    }

    private void doClaimAfterProcess(ClaimRequestVO claimRequestVO, ClaimContextVO claimContextVO) {
        ClaimAfterStrategy claimAfterStrategy = claimContextVO.getClaimAfterStrategy();
        if(claimAfterStrategy == null) return;

        claimAfterStrategy.call(claimRequestVO);
    }
}
