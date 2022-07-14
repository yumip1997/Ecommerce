package com.plateer.ec1.payment.vo.res;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.vo.ValidResVO;
import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.enums.PaymentException;

public class VacctCnlResVO implements ValidResVO {

    private String resultCode;

    @Override
    public void isValidRes() {
        if(!PaymentBusiness.VACCT_CNL.getSucessCode().equals(this.resultCode)){
            throw new BusinessException(PaymentException.FAIL_CANCEL_PAY.MSG);
        }
    }
}
