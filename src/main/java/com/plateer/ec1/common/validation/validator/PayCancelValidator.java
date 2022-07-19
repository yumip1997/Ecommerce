package com.plateer.ec1.common.validation.validator;

import com.plateer.ec1.common.validation.annotation.PayCancel;
import com.plateer.ec1.payment.enums.OPT0011Code;
import com.plateer.ec1.payment.enums.PaymentException;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PayCancelValidator implements ConstraintValidator<PayCancel, OrderPayInfoVO> {

    @Override
    public void initialize(PayCancel constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(OrderPayInfoVO value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value)){
            addConstraintViolation(context, PaymentException.NOT_FIND_ORDER.MSG);
            return false;
        }

        if(!isValidCnlAmt(value.getCnclReqAmt(), value)){
            addConstraintViolation(context, PaymentException.INVALID_CNL_AMT.MSG);
            return false;
        }

        return true;
    }

    private boolean isValidCnlAmt(long cnlReqAmt, OrderPayInfoVO orderPayInfoVO){
        String payPrgsScd = orderPayInfoVO.getPayPrgsScd();

        if(OPT0011Code.PAY_REQUEST.getCode().equals(payPrgsScd)){
            return cnlReqAmt <= orderPayInfoVO.getPayAmt();
        }

        if(OPT0011Code.PAY_COMPLETE.getCode().equals(payPrgsScd)){
            return cnlReqAmt <= orderPayInfoVO.getRfndAvlAmt();
        }

        return false;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String msg){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg)
                .addConstraintViolation();
    }

}
