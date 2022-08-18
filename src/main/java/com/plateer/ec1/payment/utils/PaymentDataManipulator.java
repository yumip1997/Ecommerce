package com.plateer.ec1.payment.utils;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.mapper.PaymentTrxMapper;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
@Validated
@LogTrace
public class PaymentDataManipulator {

    private final PaymentTrxMapper paymentTrxMapper;

    @Transactional
    public String insertOrderPayment(OpPayInfoModel opPayInfoModel){
        return paymentTrxMapper.insertOrderPayment(opPayInfoModel);
    }

    @Transactional
    public void insertVacctApprove(@Valid VacctSeqResVO resVO, OrderInfoVO orderInfoVO){
        OpPayInfoModel model = OpPayInfoModel.getInsertVacctApvData(resVO, orderInfoVO);
        insertOrderPayment(model);
    }

    @Transactional
    public void manipulateCnlAfterDeposit(@Valid VacctCnlResVO resVO, OrderPayInfoVO orderPayInfoVO){
        paymentTrxMapper.updateOrderPayment(OpPayInfoModel.getCnlUpdateData(orderPayInfoVO));
        if(orderPayInfoVO.isPartialCancel()){
            orderPayInfoVO.setTrsnId(resVO.getTid());
        }

        paymentTrxMapper.insertOrderPayment(OpPayInfoModel.getCnlCmpInsertData(orderPayInfoVO));
    }

    @Transactional
    public String manipulateCnl(OrderPayInfoVO orderPayInfoVO){
        paymentTrxMapper.updateOrderPayment(OpPayInfoModel.getCnlUpdateData(orderPayInfoVO));
        return paymentTrxMapper.insertOrderPayment(OpPayInfoModel.getCnlCmpInsertData(orderPayInfoVO));
    }
}
