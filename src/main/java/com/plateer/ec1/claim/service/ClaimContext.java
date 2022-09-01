package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.creator.ClaimDataCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.externals.ExternalIFCallHelper;
import com.plateer.ec1.claim.manipulator.ClaimDataManipulator;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.*;
import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.order.service.OrdClmMntService;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
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
    public void doClaim(ClaimRequestVO claimRequestVO, ClaimContextVO claimContextVO) {
        Long logSeq = ordClmMntService.insertOrderHistory(claimRequestVO);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = new OrdClmCreationVO<>();

        try {
            ClaimView claimView = getClaimView(claimRequestVO);

            validate(claimView, claimContextVO.getClaimBusiness(), claimContextVO.getValidatorList());

            creationVO = create(claimRequestVO, claimView);

            claimDataManipulator.manipulateClaimData(creationVO.getInsertData(), creationVO.getUpdateData());

            callExternalIF(claimView, claimContextVO.getIfCallHelperList());
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

        ClaimValidationVO validationVO = ClaimValidationVO.builder()
                .prdType(claimBusiness.getPrdTypeStr())
                .validOrdPrgs(claimBusiness.getValidOrdPrgs())
                .claimView(claimView)
                .build();

        for (ClaimValidator validator : claimValidatorList) {
            validator.validate(validationVO);
        }
    }

    private OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> create(ClaimRequestVO claimRequestVO, ClaimView claimView) {
        ClaimDataCreator claimDataCreator = ClaimDataCreator.of(claimMapper);
        return claimDataCreator.createOrdClmCreationVO(claimRequestVO, claimView);
    }

    private void callExternalIF(ClaimView claimView, List<ExternalIFCallHelper> ifCallHelperList) {
        if (CollectionUtils.isEmpty(ifCallHelperList)) return;

        for (ExternalIFCallHelper externalIFCallHelper : ifCallHelperList) {
            externalIFCallHelper.call(claimView);
        }
    }

}
