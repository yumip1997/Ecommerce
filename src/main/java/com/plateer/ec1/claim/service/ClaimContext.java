package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.creator.ClaimDataCreatorManager;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.externals.ExternalIFCallHelper;
import com.plateer.ec1.claim.manipulator.ClaimDataManipulator;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.claim.validation.verifier.AmountVerifier;
import com.plateer.ec1.claim.vo.*;
import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.order.service.OrdClmMntService;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClaimContext {

    private final ClaimMapper claimMapper;
    private final OrdClmMntService ordClmMntService;
    private final ClaimDataCreatorManager claimDataCreatorManager;
    private final ClaimDataManipulator claimDataManipulator;

    @LogTrace
    @Transactional
    public void doClaim(ClaimRequestVO claimRequestVO, ClaimContextVO claimContextVO) {
        Long logSeq = ordClmMntService.insertOrderHistory(claimRequestVO);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = new OrdClmCreationVO<>();
        ClaimBusiness claimBusiness = claimContextVO.getClaimBusiness();

        try {
            ClaimView claimView = getClaimView(claimRequestVO);

            ClaimValidator.validateAll(claimView.toClaimValidationVO(claimBusiness), claimContextVO.getValidatorList());

            creationVO = claimDataCreatorManager.createOrdClmCreationVO(claimView, claimBusiness, claimContextVO.getClaimCreatorVO());

            claimDataManipulator.manipulateClaimData(creationVO.getInsertData(), creationVO.getUpdateData());

            ExternalIFCallHelper.callAll(claimRequestVO, creationVO, claimContextVO.getCallHelperList());

            AmountVerifier.verifyAmountAll(claimRequestVO, creationVO, claimContextVO.getVerifierList());
        } catch (Exception e) {
            creationVO.setException(e);
            throw e;
        } finally {
            ordClmMntService.updateOrderHistory(logSeq, creationVO);
        }
    }

    private ClaimView getClaimView(ClaimRequestVO claimRequestVO){
        List<ClaimGoodsInfo> claimGoodsInfoWithBnf = claimMapper.getClaimGoodsWithBnfList(claimRequestVO.getClaimGoodsInfoList());

        List<ClaimDeliveryCostInfo> reqDvCstList = claimRequestVO.getClaimDeliveryCostInfoList();
        List<ClaimDeliveryCostInfo> deliveryCostInfoList = CollectionUtils.isEmpty(reqDvCstList) ?
                Collections.emptyList() : claimMapper.getClaimDeliveryCostInfoList(reqDvCstList);
        
        return claimRequestVO.toClaimView(claimGoodsInfoWithBnf, deliveryCostInfoList);
    }
}
