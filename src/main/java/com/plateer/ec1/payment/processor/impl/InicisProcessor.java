package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.enums.CommonConstants;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.OPT0011Code;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.utils.inicis.InicisApiCallHelper;
import com.plateer.ec1.payment.utils.PaymentDataManipulator;
import com.plateer.ec1.payment.utils.inicis.InicisApiConstants;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayCancelInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctDpstCmtResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

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
    public void cancelPay(PayCancelInfoVO payCancelInfoVO) {
        //TODO 주문번호 조회 시 NULL 일때 처리
        OpPayInfoModel payInfoModel = paymentDataManipulator.getOpPayInfoModelByOrdNo(payCancelInfoVO.getOrdNo());
        String type = isPartialCancel(payInfoModel.getPayAmt(), payCancelInfoVO.getCnclAmt()) ? InicisApiConstants.TYPE_PARTIAL_REFUND : InicisApiConstants.TYPE_REFUND;

        if(OPT0011Code.PAY_REQUEST.getCode().equals(payInfoModel.getPayPrgsScd())){
        }

        if(OPT0011Code.PAY_COMPLETE.getCode().equals(payInfoModel.getPayPrgsScd())){
            VacctCnlResVO vacctCnlResVO = inicisApiCallHelper.callVacctCnl(type, payInfoModel);
            paymentDataManipulator.updateCnl(vacctCnlResVO);
        }
    }

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }
}
