package com.plateer.ec1.payment.utils;

import com.plateer.ec1.common.validation.annotation.ResValid;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.mapper.PaymentMapper;
import com.plateer.ec1.payment.mapper.PaymentTrxMapper;
import com.plateer.ec1.payment.vo.inicis.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctDpstCmtResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
@Validated
public class PaymentDataManipulator {

    private final PaymentMapper paymentMapper;
    private final PaymentTrxMapper paymentTrxMapper;

    public OpPayInfoModel getOpPayInfoModelByOrdNo(String ordNo){
        return paymentMapper.getOpPayInfoByOrdNo(ordNo);
    }

    public void insertVacctApprove(String ordNo, @Valid VacctSeqResVO resVO){
        OpPayInfoModel insertData = OpPayInfoModel.getInsertData(ordNo, resVO);
        paymentTrxMapper.insertOrderPayment(insertData);
    }

    public void updateVacctApprove(@Valid VacctDpstCmtResVO resVO){
        OpPayInfoModel payCmpUpdateData = OpPayInfoModel.getPayCmpUpdateData(resVO);
        paymentTrxMapper.updateOrderPayment(payCmpUpdateData);
    }

    public void insertCnl(@Valid VacctCnlResVO resVO){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder().build();
        paymentTrxMapper.insertOrderPayment(opPayInfoModel);
    }

    public void updateCnl(@Valid VacctCnlResVO resVO){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder().build();
        paymentTrxMapper.updateOrderPayment(opPayInfoModel);
    }
}
