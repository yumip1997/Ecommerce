package com.plateer.ec1.promotion.com.validator;

import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.enums.PromotionException;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

//TODO CupInfoVO 안으로
public class CupInfoValidator {

    public static void isExistCupInfo(CupInfoVO cupInfoVO) {
        if (ObjectUtils.isEmpty(cupInfoVO)) {
            throw new RuntimeException(PromotionException.NOT_FIND_PRM.getMSG());
        }
    }

    public static void isValidPrmPeriod(CupInfoVO cupInfoVO) {
        boolean isValid = Timestamp.valueOf(LocalDateTime.now()).before(cupInfoVO.getPrmEndDt());
        if (!isValid) {
            throw new RuntimeException(PromotionException.INVALID_PRM_PERIOD.getMSG());
        }
    }

    public static void isValidCupDwlPeriod(CupInfoVO cupInfoVO) {
        boolean isValid = Timestamp.valueOf(LocalDateTime.now()).before(cupInfoVO.getDwlAvlEndDd());
        if (!isValid) {
            throw new RuntimeException(PromotionException.INVALID_CUP_DWL_PERIOD.getMSG());
        }
    }

    public static void isValidCnt(CupInfoVO cupInfoVO) {
        Long INFINITE_CNT = 0L;
        Long dwlPsbCnt = cupInfoVO.getDwlPsbCnt();
        Long psnDwlPsbCnt = cupInfoVO.getPsnDwlPsbCnt();

        boolean isValid;

        if (INFINITE_CNT.equals(dwlPsbCnt) && INFINITE_CNT.equals(psnDwlPsbCnt)) {
            isValid = true;
        } else if (INFINITE_CNT.equals(dwlPsbCnt)) {
            isValid = psnDwlPsbCnt > cupInfoVO.getMbrCnt();
        } else {
            isValid = dwlPsbCnt > cupInfoVO.getTotalCnt() && psnDwlPsbCnt > cupInfoVO.getMbrCnt();
        }

        if (!isValid) {
            throw new RuntimeException(PromotionException.INVALID_CUP_DWL_CNT.getMSG());
        }

    }

    public static void isNotUsed(CupInfoVO cupInfoVO){
        if(!ObjectUtils.isEmpty(cupInfoVO.getCpnUseDt())){
            throw new RuntimeException(PromotionException.ALREADY_USED_CUP.getMSG());
        }
    }

    public static void isUsed(CupInfoVO cupInfoVO){
        if(ObjectUtils.isEmpty(cupInfoVO.getCpnUseDt())){
            throw new RuntimeException(PromotionException.NOT_USED_CUP.getMSG());
        }
    }
}
