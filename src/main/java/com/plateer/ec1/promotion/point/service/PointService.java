package com.plateer.ec1.promotion.point.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.model.promotion.CcMbrPntModel;
import com.plateer.ec1.promotion.enums.PRM0011Code;
import com.plateer.ec1.promotion.enums.PromotionException;
import com.plateer.ec1.promotion.point.mapper.PointMapper;
import com.plateer.ec1.promotion.point.mapper.PointTrxMapper;
import com.plateer.ec1.promotion.point.validation.groups.PointRestore;
import com.plateer.ec1.promotion.point.validation.groups.PointSave;
import com.plateer.ec1.promotion.point.validation.groups.PointUse;
import com.plateer.ec1.promotion.point.vo.PointVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;
import java.util.function.BinaryOperator;

@Service
@RequiredArgsConstructor
@Validated
@LogTrace
public class PointService {

    private final PointMapper pointMapper;
    private final PointTrxMapper pointTrxMapper;

    @Validated(PointSave.class)
    @Transactional
    public void savePoint(@Valid PointVO pointVO){
        manipulatePoint(pointVO);
    }

    @Validated(PointUse.class)
    @Transactional
    public void usePoint(@Valid PointVO pointVO){
        manipulatePoint(pointVO);
    }

    @Validated(PointRestore.class)
    @Transactional
    public void restorePoint(@Valid PointVO pointVO){
        manipulatePoint(pointVO);
    }

    private void manipulatePoint(PointVO pointVO){
        Long calculatedPntBlc = calculatePntBlc(pointVO);
        if(calculatedPntBlc < 0L) throw new BusinessException(PromotionException.INVALID_POINT_REMAIN.getMSG());

        CcMbrPntModel ccMbrPntModel = pointVO.toCcMbrPntModel(calculatedPntBlc);
        pointTrxMapper.insertPoint(ccMbrPntModel);
    }

    private Long calculatePntBlc(PointVO pointVO){
        Long remainPntBlc = getPntBlc(pointVO.getMbrNo());
        BinaryOperator<Long> operator = PRM0011Code.getOperatorByCode(pointVO.getSvUseCcd());
        return operator.apply(remainPntBlc, pointVO.getSvUseAmt());
    }
    
    private Long getPntBlc(String mbrNo){
        return Optional.ofNullable(pointMapper.getPntBlcByMbrNo(mbrNo))
                .orElse(0L);
    }

}
