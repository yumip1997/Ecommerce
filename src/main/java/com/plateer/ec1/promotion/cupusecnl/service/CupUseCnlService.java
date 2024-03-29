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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
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
        if(CollectionUtils.isEmpty(cupUseRequestVOList)){
            return;
        }

        List<CupInfoVO> issuedCupInfoList = cupInfoMapper.getIssuedCupInfo(cupUseRequestVOList);

        List<CupInfoVO> validCupList = issuedCupInfoList.stream()
                .filter(CupInfoValidator::validateCupUse)
                .collect(Collectors.toList());

        String ordNo = cupUseRequestVOList.get(0).getOrdNo();
        cupUseCnlTrxMapper.updateCupUsed(getModelList(validCupList, ordNo));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Validated(CupRestore.class)
    public void restoreCup(List<@Valid CupIssVO> cupRestoreRequestVOList){
        List<CupInfoVO> issuedCupInfList = cupInfoMapper.getIssuedCupInfo(cupRestoreRequestVOList);

        List<CupInfoVO> validCupList = issuedCupInfList.stream()
                .filter(CupInfoValidator::validateRestoreCup)
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(validCupList)){
            return;
        }

        String ordNo = cupRestoreRequestVOList.get(0).getOrdNo();
        cupUseCnlTrxMapper.insertOrgCup(getModelList(validCupList, ordNo));
    }

    private List<CcCpnIssueModel> getModelList(List<CupInfoVO> cupInfoVOList, String ordNo){
        return cupInfoVOList.stream()
                .map(e -> e.toCcCpnIssueModel(ordNo))
                .collect(Collectors.toList());
    }

}
