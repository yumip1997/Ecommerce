package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.OPT0011Code;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.utils.InicisApiCallHelper;
import com.plateer.ec1.payment.utils.PaymentDataManipulator;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OriginOrderVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.res.VacctDpstCmtResVO;
import com.plateer.ec1.payment.vo.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InicisProcessor implements PaymentProcessor {

    private final InicisApiCallHelper inicisApiCallHelper;
    private final PaymentDataManipulator paymentDataManipulator;

    @Override
    public ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
        VacctSeqResVO resVO = inicisApiCallHelper.callVacctSeq(orderInfoVO, payInfoVO);

        paymentDataManipulator.insertVacctApprove(orderInfoVO.getOrdNo(), resVO);
        return new ApproveResVO(payInfoVO.getPaymentType(), resVO.getAblePartialCancelYn());
    }

    public void completeVacctDeposit(VacctDpstCmtResVO resVO){
        paymentDataManipulator.updateVacctApprove(resVO);
    }

    @Override
    public void cancelPay(OriginOrderVO originOrderVO) {
        OpPayInfoModel payInfoModel = OpPayInfoModel.builder().build();
        verifyAmount();

        VacctCnlResVO vacctCnlResVO = inicisApiCallHelper.callVacctCnl();

        if(OPT0011Code.PAY_REQUEST.getCode().equals(payInfoModel.getPayPrgsScd())){
            paymentDataManipulator.insertCnl(vacctCnlResVO);
        }

        if(OPT0011Code.PAY_COMPLETE.getCode().equals(payInfoModel.getPayPrgsScd())){
            paymentDataManipulator.updateCnl(vacctCnlResVO);
        }
    }

    private void verifyAmount(){

    }

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }
}
