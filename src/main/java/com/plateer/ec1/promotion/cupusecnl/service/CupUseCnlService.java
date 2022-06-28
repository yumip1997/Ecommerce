package com.plateer.ec1.promotion.cupusecnl.service;

import com.plateer.ec1.promotion.cupusecnl.mapper.CupUseCnlTrxMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Log4j2
public class CupUseCnlService {

    private final CupUseCnlTrxMapper cupUseCnlTrxMapper;

    //TODO 쿠폰 사용 취소 시?
    public void useCup(){

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void restoreCup(){
    }
}
