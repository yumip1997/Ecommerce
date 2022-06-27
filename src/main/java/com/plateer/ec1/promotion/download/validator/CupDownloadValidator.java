package com.plateer.ec1.promotion.download.validator;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.promotion.download.vo.CupInfoVO;
import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.enums.PRM0009Code;
import com.plateer.ec1.promotion.enums.PromotionException;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface CupDownloadValidator extends CustomFactory<PRM0009Code> {

    void isValid(CupDwlRequestVO cupDwlRequestVO) throws Exception;
    
    default void isNotEmptyMbrNo(CupDwlRequestVO cupDwlRequestVO) throws Exception {
        if(!ObjectUtils.isEmpty(cupDwlRequestVO.getMbrNo())) return;

        throw new Exception(PromotionException.NULL_CUP_MBR_NO.getMSG());
    }
    
    default void isExistCupInfo(CupInfoVO cupInfoVO) throws Exception{
        if(!ObjectUtils.isEmpty(cupInfoVO)) return;
        
        throw new Exception(PromotionException.NOT_FIND_PRM.getMSG());
    }
    
    default void isValidPeriod(CupInfoVO cupInfoVO) throws Exception {
        boolean isValid = Timestamp.valueOf(LocalDateTime.now()).before(cupInfoVO.getDwlEndDd());
        if(isValid) return;

        throw new Exception(PromotionException.INVALID_CUP_DWL_PERIOD.getMSG());
    }

    default void isValidCnt(CupInfoVO cupInfoVO) throws Exception {
        boolean isValid =  cupInfoVO.getDwlPsbCnt() > cupInfoVO.getTotalCnt()
                && cupInfoVO.getPsnDwlPsbCnt() > cupInfoVO.getMbrCnt();
        if(isValid) return;

        throw new Exception(PromotionException.INVALID_CUP_DWL_CNT.getMSG());
    }

}
