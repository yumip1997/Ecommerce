package com.plateer.ec1.payment.service;

import com.plateer.ec1.common.enums.CommonConstants;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.mapper.PaymentTrxMapper;
import com.plateer.ec1.payment.vo.inicis.res.VacctDpstCmtResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor
@Validated
public class InicisService {

    private final PaymentTrxMapper paymentTrxMapper;

    @Transactional
    public String completeVacctDeposit(@Valid VacctDpstCmtResVO resVO){
        paymentTrxMapper.updateOrderPayment(OpPayInfoModel.getPayCmpUpdateData(resVO));
        return CommonConstants.OK.getCode();
    }
}
