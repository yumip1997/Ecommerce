package com.plateer.ec1.payment.utils;

import com.plateer.ec1.common.aop.ResValid;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.mapper.PaymentMapper;
import com.plateer.ec1.payment.mapper.PaymentTrxMapper;
import com.plateer.ec1.payment.vo.inicis.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctDpstCmtResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentDataManipulator {

    private final PaymentMapper paymentMapper;
    private final PaymentTrxMapper paymentTrxMapper;

    public OpPayInfoModel getOpPayInfoModelByOrdNo(String ordNo){
        return paymentMapper.getOpPayInfoByOrdNo(ordNo);
    }

    @ResValid
    public void insertVacctApprove(String ordNo, VacctSeqResVO resVO){
        OpPayInfoModel insertData = OpPayInfoModel.getInsertData(ordNo, resVO);
        paymentTrxMapper.insertOrderPayment(insertData);
    }

    @ResValid
    public void updateVacctApprove(VacctDpstCmtResVO resVO){
        OpPayInfoModel payCmpUpdateData = OpPayInfoModel.getPayCmpUpdateData(resVO);
        paymentTrxMapper.updateOrderPayment(payCmpUpdateData);
    }

    @ResValid
    public void insertCnl(VacctCnlResVO resVO){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder().build();
        paymentTrxMapper.insertOrderPayment(opPayInfoModel);
    }

    @ResValid
    public void updateCnl(VacctCnlResVO resVO){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder().build();
        paymentTrxMapper.updateOrderPayment(opPayInfoModel);
    }
}
