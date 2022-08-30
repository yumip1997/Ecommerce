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
            validate(claimRequestVO, claimContextVO.getClaimBusiness(), claimContextVO.getValidatorList());

            creationVO = create(claimRequestVO);

            claimDataManipulator.manipulateClaimData(creationVO.getInsertData(), creationVO.getUpdateData());

            callExternalIF(claimRequestVO, claimContextVO.getIfCallHelperList());
        }catch (Exception e){
            creationVO.setException(e);
            throw e;
        }finally {
            ordClmMntService.updateOrderHistory(logSeq, creationVO);
        }
    }

    private void validate(ClaimRequestVO claimRequestVO, ClaimBusiness claimBusiness, List<ClaimValidator> claimValidatorList) {
       if(CollectionUtils.isEmpty(claimValidatorList)) return;

        List<ClaimView> claimViewList = claimMapper.getClaimViewList(claimRequestVO.getClaimGoodsInfoList());

        ClaimValidationVO validationVO = ClaimValidationVO.builder()
                .prdType(claimBusiness.getPrdTypeStr())
                .validOrdPrgs(claimBusiness.getValidOrdPrgs())
                .claimViewList(claimViewList)
                .build();

        for (ClaimValidator validator : claimValidatorList) {
            validator.validate(validationVO);
        }

    }

    private OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> create(ClaimRequestVO claimRequestVO){
        ClaimDataCreator claimDataCreator = ClaimDataCreator.of(claimMapper);
        return claimDataCreator.createOrdClmCreationVO(claimRequestVO);
    }

    private void callExternalIF(ClaimRequestVO claimRequestVO, List<ExternalIFCallHelper> ifCallHelperList) {
        if(CollectionUtils.isEmpty(ifCallHelperList)) return;

        for (ExternalIFCallHelper externalIFCallHelper : ifCallHelperList) {
            externalIFCallHelper.call(claimRequestVO);
        }
    }

}
