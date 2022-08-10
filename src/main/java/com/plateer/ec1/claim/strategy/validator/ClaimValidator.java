package com.plateer.ec1.claim.strategy.validator;

import com.plateer.ec1.claim.enums.ClaimException;
import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.ValidCode;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.order.vo.OrderProductVO;

import java.util.List;

public interface ClaimValidator extends CustomFactory<ClaimStatusType> {

    void isValid(ClaimRequestVO claimRequestVO, ValidCode validCode);

    void verifyAmount(ClaimRequestVO claimRequestVO);

    default void isValidOrdPrgsScd(ClaimRequestVO claimRequestVO, List<String> validOrdPrgs){
        for (ClaimBaseVO claimBaseVO : claimRequestVO.getClaimBaseVOList()) {
            if(isContainInList(validOrdPrgs, claimBaseVO.getOrdPrgsScd())) continue;;

            throw new ValidationException(ClaimException.INVALID_ORDER_STATUS.MSG);
        }
    }

    default void isValidProductType(ClaimRequestVO claimRequestVO, String prdType){
        for (OrderProductVO orderProductVO : claimRequestVO.getOrderProductVOList()) {
            if(prdType.equals(orderProductVO.getGoodsTpCd())) continue;

            throw new ValidationException(ClaimException.INVALID_PRD_TPYE.MSG);
        }
    }

    default boolean isContainInList(List<String> list, String target){
        return list.contains(target);
    }

}

