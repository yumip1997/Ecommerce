package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.enums.CommonConstants;
import com.plateer.ec1.payment.enums.OPT0011Code;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.utils.PaymentDataManipulator;
import com.plateer.ec1.payment.utils.inicis.InicisApiCallHelper;
import com.plateer.ec1.payment.utils.inicis.InicisApiConstants;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctDpstCmtResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class InicisProcessor implements PaymentProcessor {

    private final InicisApiCallHelper inicisApiCallHelper;
    private final PaymentDataManipulator paymentDataManipulator;

    @Override
    public ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
        VacctSeqResVO resVO = inicisApiCallHelper.callVacctSeq(orderInfoVO, payInfoVO);
        paymentDataManipulator.insertVacctApprove(orderInfoVO.getOrdNo(), resVO);

        return new ApproveResVO(payInfoVO.getPaymentType(), resVO.getAblePartialCancelYn());
    }

    public String completeVacctDeposit(VacctDpstCmtResVO resVO){
        paymentDataManipulator.updateVacctApprove(resVO);
        return CommonConstants.OK.getCode();
    }

    @Override
    public void cancelPay(OrderPayInfoVO orderPayInfoVO) {
        String type = isPartialCancel(orderPayInfoVO.getPayAmt(), orderPayInfoVO.getCnclAmt()) ? InicisApiConstants.TYPE_PARTIAL_REFUND : InicisApiConstants.TYPE_REFUND;

        if(OPT0011Code.PAY_REQUEST.getCode().equals(orderPayInfoVO.getPayPrgsScd())){
        }

        if(OPT0011Code.PAY_COMPLETE.getCode().equals(orderPayInfoVO.getPayPrgsScd())){
            VacctCnlResVO vacctCnlResVO = inicisApiCallHelper.callVacctCnl(type, orderPayInfoVO);
            paymentDataManipulator.updateCnl(vacctCnlResVO);
        }
    }

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }
}
