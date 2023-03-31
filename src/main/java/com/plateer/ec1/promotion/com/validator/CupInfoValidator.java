package com.plateer.ec1.promotion.com.validator;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.enums.PromotionException;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CupInfoValidator {

    public static boolean validateCupUse(CupInfoVO cupInfoVO) {
        return isExistCup(cupInfoVO) && isValidPrmPeriod(cupInfoVO) && !isUsed(cupInfoVO);
    }

    public static boolean validateRestoreCup(CupInfoVO cupInfoVO) {
        return isExistCup(cupInfoVO) && isValidPrmPeriod(cupInfoVO) && isUsed(cupInfoVO);
    }

    public static void validateDwlCup(CupInfoVO cupInfoVO) {
        isNotExistCupInfoWithException(cupInfoVO);
        isValidCupDwlPeriodWithException(cupInfoVO);
        isValidCntWithException(cupInfoVO);
    }

    private static boolean isExistCup(CupInfoVO cupInfoVO) {
        return ObjectUtils.isNotEmpty(cupInfoVO);
    }

    private static boolean isValidPrmPeriod(CupInfoVO cupInfoVO) {
        return LocalDateTime.now().isBefore(cupInfoVO.getPrmEndDt());
    }

    private static boolean isValidCupDwlPeriod(CupInfoVO cupInfoVO) {
        return LocalDate.now().isBefore(cupInfoVO.getDwlAvlEndDd());
    }

    private static boolean isValidCnt(CupInfoVO cupInfoVO) {
        final Long INFINITE_CNT = 0L;
        Long dwlPsbCnt = INFINITE_CNT.equals(cupInfoVO.getDwlPsbCnt()) ? Long.MAX_VALUE : cupInfoVO.getDwlPsbCnt();
        Long psnDwlPsbCnt = INFINITE_CNT.equals(cupInfoVO.getPsnDwlPsbCnt()) ? Long.MAX_VALUE : cupInfoVO.getPsnDwlPsbCnt();

        return dwlPsbCnt > cupInfoVO.getTotalCnt() && psnDwlPsbCnt > cupInfoVO.getMbrCnt();
    }

    private static boolean isUsed(CupInfoVO cupInfoVO) {
        return ObjectUtils.isNotEmpty(cupInfoVO.getCpnUseDt());
    }

    public static void isNotExistCupInfoWithException(CupInfoVO cupInfoVO) {
        if (!isExistCup(cupInfoVO)) {
            throw new BusinessException(PromotionException.NOT_FIND_PRM.getMSG());
        }
    }

    public static void isValidCupDwlPeriodWithException(CupInfoVO cupInfoVO) {
        if (!isValidCupDwlPeriod(cupInfoVO)) {
            throw new BusinessException(PromotionException.INVALID_CUP_DWL_PERIOD.getMSG());
        }
    }

    public static void isValidCntWithException(CupInfoVO cupInfoVO) {
        if (!isValidCnt(cupInfoVO)) {
            throw new BusinessException(PromotionException.INVALID_CUP_DWL_CNT.getMSG());
        }
    }
}
