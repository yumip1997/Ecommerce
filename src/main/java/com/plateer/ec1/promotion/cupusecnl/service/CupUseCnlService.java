package com.plateer.ec1.promotion.cupusecnl.service;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.com.mapper.CupInfoMapper;
import com.plateer.ec1.promotion.com.validator.CupInfoValidator;
import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.cupusecnl.mapper.CupUseCnlTrxMapper;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupRestoreRequestVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupUseRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
@Log4j2
@Validated
public class CupUseCnlService {

    private final CupInfoMapper cupInfoMapper;
    private final CupUseCnlTrxMapper cupUseCnlTrxMapper;

    @Transactional
    public void useCup(@Valid CupUseRequestVO cupUseRequestVO){
        CupInfoVO cupInfoVO = cupInfoMapper.getIssuedCupInfo(cupUseRequestVO.getCpnIssNo());
        validateUseCup(cupInfoVO);

        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.convertModel(cupUseRequestVO);
        cupUseCnlTrxMapper.updateCupUsed(ccCpnIssueModel);
    }

    private void validateUseCup(CupInfoVO cupInfoVO){
        CupInfoValidator.isExistCupInfo(cupInfoVO);
        CupInfoValidator.isNotUsed(cupInfoVO);
        CupInfoValidator.isValidPrmPeriod(cupInfoVO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void restoreCup(@Valid CupRestoreRequestVO cupRestoreRequestVO){
        CupInfoVO cupInfoVO = cupInfoMapper.getIssuedCupInfo(cupRestoreRequestVO.getCpnIssNo());
        validateRestoreCup(cupInfoVO);
        
        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.convertModel(cupInfoVO);
        cupUseCnlTrxMapper.insertOrgCup(ccCpnIssueModel);
    }

    private void validateRestoreCup(CupInfoVO cupInfoVO){
        CupInfoValidator.isExistCupInfo(cupInfoVO);
        CupInfoValidator.isUsed(cupInfoVO);
        CupInfoValidator.isValidPrmPeriod(cupInfoVO);
    }

}
