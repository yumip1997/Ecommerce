package com.plateer.ec1.promotion.download.validator;

import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.vo.CupDwlVO;
import com.plateer.ec1.promotion.apply.vo.request.CupDwlRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Log4j2
public class CupDownloadValidator {

    private final CupDwlMapper cupDwlMapper;

    public void isValid(CupDwlRequestVO cupDwlRequestVO){
        checkNotEmpty(cupDwlRequestVO);
        checkValid(cupDwlRequestVO);
    }

    private void checkNotEmpty(CupDwlRequestVO cupDwlRequestVO){
        isNotEmptyPrmNo(cupDwlRequestVO);
        isNotEmptyMbrNo(cupDwlRequestVO);
    }

    private void isNotEmptyPrmNo(CupDwlRequestVO cupDwlRequestVO){
        log.info("프로모션 번호 체크");
    }

    private void isNotEmptyMbrNo(CupDwlRequestVO cupDwlRequestVO){
        log.info("회원번호 not null 체크");
    }

    private void checkValid(CupDwlRequestVO cupDwlRequestVO){
        CupDwlVO cupDwlVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);

        isValidPeriod(cupDwlVO);
        isValidCnt(cupDwlVO);
    }

    private void isValidPeriod(CupDwlVO cupDwlVO){
        log.info("기간 체크 유효성 검사 실행");
        boolean isValid = Timestamp.valueOf(LocalDateTime.now()).before(cupDwlVO.getDwlEndDd());
        if(isValid) return;

    }

    private void isValidCnt(CupDwlVO cupDwlVO){
        log.info("다운로드 가능 수량 유효성 검사 실행");
    }


}
