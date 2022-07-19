package com.plateer.ec1.payment.vo.inicis.res;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.validation.annotation.ResValid;
import com.plateer.ec1.common.vo.ValidResVO;
import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.enums.PaymentException;

@ResValid
public class VacctCnlResVO implements ValidResVO {

    private String resultCode;

    @Override
    public boolean isValidRes() {
        return PaymentBusiness.VACCT_CNL.getSucessCode().equals(this.resultCode);
    }
}
