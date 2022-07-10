package com.plateer.ec1.promotion.com.validator;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.enums.PromotionException;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

//TODO 삭제예정
public class CupInfoValidator {

    public static void cupUseValidate(CupInfoVO cupInfoVO, String mbrNo){
        isExistCupInfo(cupInfoVO);
        isEqualMbrNo(cupInfoVO, mbrNo);
        isNotUsed(cupInfoVO);
        isValidPrmPeriod(cupInfoVO);
    }

    public static void restoreCupValidate(CupInfoVO cupInfoVO, String mbrNo){
        isExistCupInfo(cupInfoVO);
        isEqualMbrNo(cupInfoVO, mbrNo);
        isUsed(cupInfoVO);
        isValidPrmPeriod(cupInfoVO);
    }

    public static void dwlValidate(CupInfoVO cupInfoVO){
        isExistCupInfo(cupInfoVO);
        isValidCupDwlPeriod(cupInfoVO);
        isValidCnt(cupInfoVO);
    }

    private static void isEqualMbrNo(CupInfoVO cupInfoVO, String mbrNo) {
        if(!mbrNo.equals(cupInfoVO.getMbrNo())){
            throw new BusinessException(PromotionException.NOT_SAME_MBR_NO.getMSG());
        }
    }

    public static void isExistCupInfo(CupInfoVO cupInfoVO) {
        if (ObjectUtils.isEmpty(cupInfoVO)) {
            throw new BusinessException(PromotionException.NOT_FIND_PRM.getMSG());
        }
    }

    public static void isValidPrmPeriod(CupInfoVO cupInfoVO) {
        if (LocalDateTime.now().isAfter(cupInfoVO.getPrmEndDt())) {
            throw new BusinessException(PromotionException.INVALID_PRM_PERIOD.getMSG());
        }
    }

    public static void isValidCupDwlPeriod(CupInfoVO cupInfoVO) {
        if (LocalDate.now().isAfter(cupInfoVO.getDwlAvlEndDd())) {
            throw new BusinessException(PromotionException.INVALID_CUP_DWL_PERIOD.getMSG());
        }
    }

    public static void isValidCnt(CupInfoVO cupInfoVO) {
        final Long INFINITE_CNT = 0L;
        Long dwlPsbCnt = INFINITE_CNT.equals(cupInfoVO.getDwlPsbCnt()) ? Long.MAX_VALUE : cupInfoVO.getDwlPsbCnt();
        Long psnDwlPsbCnt = INFINITE_CNT.equals(cupInfoVO.getPsnDwlPsbCnt()) ? Long.MAX_VALUE : cupInfoVO.getPsnDwlPsbCnt();

        boolean isValid = dwlPsbCnt > cupInfoVO.getTotalCnt() && psnDwlPsbCnt > cupInfoVO.getMbrCnt();

        if (!isValid) {
            throw new BusinessException(PromotionException.INVALID_CUP_DWL_CNT.getMSG());
        }
    }

    public static void isNotUsed(CupInfoVO cupInfoVO){
        if(!ObjectUtils.isEmpty(cupInfoVO.getCpnUseDt())){
            throw new BusinessException(PromotionException.ALREADY_USED_CUP.getMSG());
        }
    }

    public static void isUsed(CupInfoVO cupInfoVO){
        if(ObjectUtils.isEmpty(cupInfoVO.getCpnUseDt())){
            throw new BusinessException(PromotionException.NOT_USED_CUP.getMSG());
        }
    }
}
