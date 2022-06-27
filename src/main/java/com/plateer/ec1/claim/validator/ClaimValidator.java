package com.plateer.ec1.claim.validator;
import com.plateer.ec1.claim.enums.*;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.order.OpClmInfo;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ClaimValidator implements CustomFactory<ValidatorType> {

    abstract public void isValid(ClaimBaseVO claimBaseVO) throws Exception;

    abstract public void verifyAmount(ClaimBaseVO claimBaseVO) throws Exception;

    public void isValidOrdPrgsScd(ClaimBaseVO claimBaseVO) throws Exception {
        OpClmInfo currentClaim = OpClmInfo.builder().ordPrgsScd(OrdPrgsScd.ORDER_COMPLETE.code).build();

        String currentClaimProcess = currentClaim.getOrdPrgsScd();
        List<String> validStatusList =  ClaimDefine.findClaimValidStatusCode(claimBaseVO)
                .getValidOrdPrgsScd()
                .stream()
                .map(OrdPrgsScd::getCode)
                .collect(Collectors.toList());

        if(isContainInList(validStatusList, currentClaimProcess)) return;

        throw new Exception(ClaimException.INVALID_ORDER_STATUS.EXCEPTION_MSG);
    }

    public void isValidProductType(ClaimBaseVO claimBaseVO) throws Exception{
        String currentPrdType = claimBaseVO.getGoodsSellTpCd();
        List<String> validPrdTypeList = ClaimDefine.findClaimValidStatusCode(claimBaseVO)
                .getValidSellTpCd()
                .stream()
                .map(GoodsSellTpCd::getCode)
                .collect(Collectors.toList());

        if(isContainInList(validPrdTypeList, currentPrdType)) return;

        throw new Exception(ClaimException.INVALID_PRD_TPYE.EXCEPTION_MSG);
    }

    private boolean isContainInList(List<String> list, String target){
        return list.contains(target);
    }

}

