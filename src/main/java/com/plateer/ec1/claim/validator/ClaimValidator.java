package com.plateer.ec1.claim.validator;
import com.plateer.ec1.claim.enums.ClaimDefine;
import com.plateer.ec1.claim.enums.ClaimException;
import com.plateer.ec1.claim.enums.ClaimProcessorType;
import com.plateer.ec1.claim.enums.OrdPrgsScdCode;
import com.plateer.ec1.claim.vo.ClaimVO;
import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.order.OrderClaim;

import java.util.List;

public abstract class ClaimValidator implements CustomFactory<ClaimProcessorType> {

    abstract public void isValid(ClaimVO claimVO) throws Exception;

    abstract public void verifyAmount(ClaimVO claimVO) throws Exception;

    public void isValidOrdPrgsScd(ClaimVO claimVO) throws Exception {
        OrderClaim currentClaim = OrderClaim.builder().ordPrgsScd(OrdPrgsScdCode.ORDER_COMPLETE.code).build();

        String currentClaimProcess = currentClaim.getOrdPrgsScd();
        List<String> validStatusList =  ClaimDefine.findClaimValidStatusCode(claimVO)
                .getClaimValidProcessCode()
                .getValidOrderStatusList();

        if(isContainInList(validStatusList, currentClaimProcess)) return;

        throw new Exception(ClaimException.INVALID_ORDER_STATUS.EXCEPTION_MSG);
    }

    public void isValidProductType(ClaimVO claimVO) throws Exception{
        String currentPrdType = claimVO.getProductType();
        List<String> validPrdTypeList = ClaimDefine.findClaimValidStatusCode(claimVO)
                .getClaimValidProdutTypeCode()
                .getValidProductTypeList();

        if(isContainInList(validPrdTypeList, currentPrdType)) return;

        throw new Exception(ClaimException.INVALID_PRD_TPYE.EXCEPTION_MSG);
    }

    private boolean isContainInList(List<String> list, String target){
        return list.contains(target);
    }

}

