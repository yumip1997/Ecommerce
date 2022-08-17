package com.plateer.ec1.promotion.cupusecnl.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import com.plateer.ec1.promotion.com.mapper.CupInfoMapper;
import com.plateer.ec1.promotion.com.validation.CupRestore;
import com.plateer.ec1.promotion.com.validation.CupUse;
import com.plateer.ec1.promotion.com.validator.CupInfoValidator;
import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.cupusecnl.mapper.CupUseCnlTrxMapper;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupRestoreRequestVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupUseRequestVO;
import com.plateer.ec1.promotion.enums.PromotionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@LogTrace
@Validated
public class CupUseCnlService {

    private final CupInfoMapper cupInfoMapper;
    private final CupUseCnlTrxMapper cupUseCnlTrxMapper;

    @Transactional
    @Validated(CupUse.class)
    public void useCup(List<@Valid CupIssVO> cupUseRequestVOList){
        List<CupInfoVO> issuedCupInfo = cupInfoMapper.getIssuedCupInfo(cupUseRequestVOList);

        CupInfoValidator.validateCupUse(issuedCupInfo);

        cupUseCnlTrxMapper.updateCupUsed(getModelList(issuedCupInfo));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Validated(CupRestore.class)
    public void restoreCup(List<@Valid CupIssVO> cupRestoreRequestVOList){
        List<CupInfoVO> issuedCupInfo = cupInfoMapper.getIssuedCupInfo(cupRestoreRequestVOList);

        CupInfoValidator.validateRestoreCup(issuedCupInfo);

        cupUseCnlTrxMapper.insertOrgCup(getModelList(issuedCupInfo));
    }

    private List<CcCpnIssueModel> getModelList(List<CupInfoVO> cupInfoVOList){
        return cupInfoVOList.stream()
                .map(CcCpnIssueModel::convertModel)
                .collect(Collectors.toList());
    }

}
