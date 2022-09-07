package com.plateer.ec1.claim.validation.verifier.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.validation.verifier.AmountVerifier;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

@Component
@RequiredArgsConstructor
public class RefundableAmountVerifier implements AmountVerifier {

    private final ClaimMapper claimMapper;

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCC, GRW, GEW);
    }

    @Override
    public void verifyAmount(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO) {
        Boolean isValid = claimMapper.verifyRfndAvlAmt(creationVO.getOrdNo());

        if(isValid == null || !isValid){
            throw new BusinessException(OrderException.INVALID_AMT.msg);
        }
    }
}