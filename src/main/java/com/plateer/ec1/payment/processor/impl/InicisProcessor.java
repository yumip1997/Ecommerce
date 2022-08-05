package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.excpetion.custom.PaymentException;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.utils.PaymentDataManipulator;
import com.plateer.ec1.payment.utils.inicis.InicisApiCallHelper;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class InicisProcessor implements PaymentProcessor {

    private final InicisApiCallHelper inicisApiCallHelper;
    private final PaymentDataManipulator paymentDataManipulator;

    @Override
    @Transactional
    public ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
       try{
           VacctSeqResVO resVO = inicisApiCallHelper.callVacctSeq(orderInfoVO, payInfoVO);
           paymentDataManipulator.insertVacctApprove(resVO, orderInfoVO);
           return new ApproveResVO(payInfoVO.getPaymentType(), resVO.getAblePartialCancelYn());
       }catch (Exception e){
           throw new PaymentException(e.getMessage());
       }
    }

    @Override
    @Transactional
    public void cancelPay(OrderPayInfoVO orderPayInfoVO) {
        if(orderPayInfoVO.isPayRequest()){
            cancelPayBeforeDeposit(orderPayInfoVO);
        }
        if(orderPayInfoVO.isPayComplete()){
            cancelPayAfterDeposit(orderPayInfoVO);
        }
    }

    private void cancelPayBeforeDeposit(OrderPayInfoVO orderPayInfoVO){
        if(orderPayInfoVO.isPartialCancel()){
            paymentDataManipulator.manipulateCnl(orderPayInfoVO.changeCnlReqAmt(orderPayInfoVO.getPayAmt()));
            approvePay(orderPayInfoVO.toOrderInfoVO(), orderPayInfoVO.toPayInfoVO());
        }else{
            paymentDataManipulator.manipulateCnl(orderPayInfoVO);
        }
    }

    private void cancelPayAfterDeposit(OrderPayInfoVO orderPayInfoVO){
        VacctCnlResVO vacctCnlResVO = inicisApiCallHelper.callVacctCnl(orderPayInfoVO);
        paymentDataManipulator.manipulateCnlAfterDeposit(vacctCnlResVO, orderPayInfoVO);
    }

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }
}
