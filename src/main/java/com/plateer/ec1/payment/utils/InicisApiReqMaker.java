package com.plateer.ec1.payment.utils;

import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.req.VacctCnlReqVO;
import com.plateer.ec1.payment.vo.req.VacctSeqReqVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InicisApiReqMaker {

    @Value("${payment.inicis.api-key}")
    private String API_KEY;
    @Value("${payment.inicis.mid}")
    private String MID;

    public VacctSeqReqVO makeVacctSeqReqVO(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO){
        VacctSeqReqVO req = VacctSeqReqVO.of(orderInfoVO, payInfoVO);
        req.setUpVirtualAccountReqVO(API_KEY, MID);
        return req;
    }

    public VacctCnlReqVO makeVacctCnlReqVO(){
        return new VacctCnlReqVO();
    }
}
