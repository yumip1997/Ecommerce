package com.plateer.ec1.payment.utils;

import com.plateer.ec1.common.aop.ResValid;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.mapper.PaymentTrxMapper;
import com.plateer.ec1.payment.vo.res.VacctDpstCmtResVO;
import com.plateer.ec1.payment.vo.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentDataManipulator {

    private final PaymentTrxMapper paymentTrxMapper;

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

    public void insertCnl(){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder().build();
        paymentTrxMapper.insertOrderPayment(opPayInfoModel);
    }
    public void updateCnl(){
        OpPayInfoModel opPayInfoModel = OpPayInfoModel.builder().build();
        paymentTrxMapper.updateOrderPayment(opPayInfoModel);
    }
}
