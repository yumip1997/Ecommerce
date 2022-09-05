package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.externals.PaymentIFCallHelper;
import com.plateer.ec1.claim.externals.PromotionIFCallHelper;
import com.plateer.ec1.claim.manipulator.ClaimDataManipulator;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.*;
import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.order.service.OrdClmMntService;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClaimContext {

    private final ClaimMapper claimMapper;
    private final OrdClmMntService ordClmMntService;
    private final ClaimDataManipulator claimDataManipulator;
    private final PaymentIFCallHelper paymentIFCallHelper;
    private final PromotionIFCallHelper promotionIFCallHelper;

    @LogTrace
    @Transactional
    public void doClaim(ClaimRequestVO claimRequestVO, ClaimContextVO claimContextVO) {
        Long logSeq = ordClmMntService.insertOrderHistory(claimRequestVO);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = new OrdClmCreationVO<>();

        try {
            ClaimView claimView = getClaimView(claimRequestVO);

            validate(claimView, claimContextVO.getClaimBusiness(), claimContextVO.getValidatorList());

            creationVO = create(claimContextVO.getClaimBusiness(), claimView);

            claimDataManipulator.manipulateClaimData(creationVO.getInsertData(), creationVO.getUpdateData());

            callExternalIF(claimContextVO.getClaimBusiness(), claimRequestVO, creationVO);
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

    private void validate(ClaimView claimView, ClaimBusiness claimBusiness, List<ClaimValidator> claimValidatorList) {
        if (CollectionUtils.isEmpty(claimValidatorList)) return;

        ClaimValidationVO claimValidationVO = claimView.toClaimValidationVO(claimBusiness);
        for (ClaimValidator validator : claimValidatorList) {
            validator.validate(claimValidationVO);
        }
    }

    private OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> create(ClaimBusiness claimBusiness, ClaimView claimView) {
        ClaimDataCreator claimDataCreator = ClaimDataCreator.of(claimMapper);
        return claimDataCreator.createOrdClmCreationVO(claimBusiness, claimView);
    }

    private void callExternalIF(ClaimBusiness claimBusiness, ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO) {
        callPaymentIF(claimBusiness, claimRequestVO, creationVO.getClmNo());
        callPromotionIF(claimBusiness, claimRequestVO, creationVO.getUpdateData());
    }

    private void callPaymentIF(ClaimBusiness claimBusiness, ClaimRequestVO claimRequestVO, String clmNo){
        if(!paymentIFCallHelper.hasType(claimBusiness)) return;

        paymentIFCallHelper.call(claimRequestVO.toPaymentCancelReqVO(clmNo));
    }

    private void callPromotionIF(ClaimBusiness claimBusiness, ClaimRequestVO claimRequestVO, ClaimUpdateBase claimUpdateBase){
        if(!promotionIFCallHelper.hasType(claimBusiness)) return;

        List<CupIssVO> cupIssVOList = claimRequestVO.toCupIssVOList(claimUpdateBase.getRestoreCpnIssNoList());
        promotionIFCallHelper.call(cupIssVOList);
    }
}
