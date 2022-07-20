package com.plateer.ec1.payment.utils;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.mapper.PaymentTrxMapper;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
@Validated
public class PaymentDataManipulator {

    private final PaymentTrxMapper paymentTrxMapper;

    public void insertVacctApprove(OrderInfoVO orderInfoVO, @Valid VacctSeqResVO resVO){
        paymentTrxMapper.insertOrderPayment(OpPayInfoModel.getInsertVacctApvData(orderInfoVO, resVO));
    }

    public void manipulateCnlAfterDeposit(@Valid VacctCnlResVO resVO, OrderPayInfoVO orderPayInfoVO){
        manipulateCnl(orderPayInfoVO);
    }

    public void manipulateCnl(OrderPayInfoVO orderPayInfoVO){
        paymentTrxMapper.updateOrderPayment(OpPayInfoModel.getCnlUpdateData(orderPayInfoVO));
        paymentTrxMapper.insertOrderPayment(OpPayInfoModel.getCnlCmpInsertData(orderPayInfoVO));
    }
}
