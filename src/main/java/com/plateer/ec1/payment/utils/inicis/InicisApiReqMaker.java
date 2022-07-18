package com.plateer.ec1.payment.utils.inicis;

import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.inicis.req.VacctCnlReqVO;
import com.plateer.ec1.payment.vo.inicis.req.VacctSeqReqVO;
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
        req.setUpVirtualAccountReqVO(MID, API_KEY);
        return req;
    }

    public VacctCnlReqVO makeVacctCnlReqVO(){
        return new VacctCnlReqVO();
    }
}
