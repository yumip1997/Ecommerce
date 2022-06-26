package com.plateer.ec1.promotion.download.validator;

import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import com.plateer.ec1.promotion.download.vo.CupDwlVO;
import com.plateer.ec1.promotion.apply.vo.request.CupDwlRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

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

    //1. 프로모션 번호 체크
    private void isNotEmptyPrmNo(CupDwlRequestVO cupDwlRequestVO){
        log.info("프로모션 번호 체크");
    }

    //2. 회원번호 체크
    private void isNotEmptyMbrNo(CupDwlRequestVO cupDwlRequestVO){
        log.info("회원번호 not null 체크");
    }

    private void checkValid(CupDwlRequestVO cupDwlRequestVO){
//        CupDwlVO cupDwlVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO.getPrmNo());
        CupDwlVO cupDwlVO = new CupDwlVO();
        isValidPeriod(cupDwlRequestVO, cupDwlVO);
        isValidCnt(cupDwlRequestVO, cupDwlVO);
        isValidMember(cupDwlRequestVO, cupDwlVO);
    }

    //3. 기간체크
    private void isValidPeriod(CupDwlRequestVO cupDwlRequestVO, CupDwlVO cupDwlVO){
        log.info("기간 체크 유효성 검사 실행");
    }

    //4. 수량체크
    private void isValidCnt(CupDwlRequestVO cupDwlRequestVO, CupDwlVO cupDwlVO){
        log.info("다운로드 가능 수량 유효성 검사 실행");
    }

    //5. 가능한 등급인지 확인(임직원)
    private void isValidMember(CupDwlRequestVO cupDwlRequestVO, CupDwlVO cupDwlVO){
        log.info("가능 등급 유효성 검사 실행");
    }

}
