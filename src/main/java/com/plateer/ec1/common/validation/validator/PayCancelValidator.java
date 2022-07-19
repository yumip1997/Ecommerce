package com.plateer.ec1.common.validation.validator;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.common.validation.annotation.PayCancel;
import com.plateer.ec1.payment.enums.OPT0011Code;
import com.plateer.ec1.payment.enums.PaymentException;
import com.plateer.ec1.payment.mapper.PaymentMapper;
import com.plateer.ec1.payment.vo.PayCancelInfoVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class PayCancelValidator implements ConstraintValidator<PayCancel, PayCancelInfoVO> {

    private final PaymentMapper paymentMapper;

    @Override
    public void initialize(PayCancel constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(PayCancelInfoVO value, ConstraintValidatorContext context) {
        OpPayInfoModel model = paymentMapper.getOpPayInfoByOrdNo(value.getOrdNo());

        if(ObjectUtils.isEmpty(model)){
            addConstraintViolation(context, PaymentException.NOT_FIND_ORDER.MSG);
            return false;
        }

        if(!isValidCnlAmt(value.getCnclAmt(), model)){
            addConstraintViolation(context, PaymentException.INVALID_CNL_AMT.MSG);
            return false;
        }

        return true;
    }

    private boolean isValidCnlAmt(long cnlAmt, OpPayInfoModel model){
        String payPrgsScd = model.getPayPrgsScd();

        if(OPT0011Code.PAY_REQUEST.getCode().equals(payPrgsScd)){
            return cnlAmt <= model.getPayAmt();
        }

        if(OPT0011Code.PAY_COMPLETE.getCode().equals(payPrgsScd)){
            return cnlAmt <= model.getRfndAvlAmt();
        }

        return false;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String msg){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(msg)
                .addConstraintViolation();
    }

}
