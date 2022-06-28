package com.plateer.ec1.promotion.download.validator;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.download.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import com.plateer.ec1.promotion.enums.PromotionException;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public interface CupDownloadValidator extends CustomFactory<PRM0009Code> {
    void isValid(@Valid CupDwlRequestVO cupDwlRequestVO);

    default void isExistCupInfo(CupInfoVO cupInfoVO){
        if(ObjectUtils.isEmpty(cupInfoVO)){
            throw new RuntimeException(PromotionException.NOT_FIND_PRM.getMSG());
        }
    }
    
    default void isValidCupDwlPeriod(CupInfoVO cupInfoVO){
        boolean isValid = Timestamp.valueOf(LocalDateTime.now()).before(cupInfoVO.getDwlAvlEndDd());
        if(!isValid){
            throw new RuntimeException(PromotionException.INVALID_CUP_DWL_PERIOD.getMSG());
        }
    }

    default void isValidCnt(CupInfoVO cupInfoVO){
        Long INFINITE_CNT = 0L;
        Long dwlPsbCnt = cupInfoVO.getDwlPsbCnt();
        Long psnDwlPsbCnt = cupInfoVO.getPsnDwlPsbCnt();

        boolean isValid;

        if(INFINITE_CNT.equals(dwlPsbCnt) && INFINITE_CNT.equals(psnDwlPsbCnt)){
            isValid = true; //전체 무한 , 개인 무한 -> 무조건 true
        }else if(INFINITE_CNT.equals(dwlPsbCnt)){
            isValid = psnDwlPsbCnt > cupInfoVO.getMbrCnt(); //전체만 무한
        }else{
            isValid = dwlPsbCnt > cupInfoVO.getTotalCnt() && psnDwlPsbCnt > cupInfoVO.getMbrCnt();
        }

        if(!isValid) {
            throw new RuntimeException(PromotionException.INVALID_CUP_DWL_CNT.getMSG());
        }
    }

}
