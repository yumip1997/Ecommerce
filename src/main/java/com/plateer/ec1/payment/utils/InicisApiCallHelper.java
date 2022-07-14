package com.plateer.ec1.payment.utils;

import com.plateer.ec1.common.utils.ObjectMapperUtil;
import com.plateer.ec1.common.utils.RestTemplateUtil;
import com.plateer.ec1.common.vo.RestTemplateReqVO;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.req.VacctCnlReqVO;
import com.plateer.ec1.payment.vo.req.VacctSeqReqVO;
import com.plateer.ec1.payment.vo.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
@Component
public class InicisApiCallHelper {

    private final InicisApiReqMaker inicisApiReqMaker;
    private final RestTemplateUtil restTemplateUtil;

    public VacctSeqResVO callVacctSeq(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
        VacctSeqReqVO vacctSeqReqVO = inicisApiReqMaker.makeVacctSeqReqVO(orderInfoVO, payInfoVO);

        RestTemplateReqVO<MultiValueMap<String, String>> restTemplateReqVO = RestTemplateReqVO.<MultiValueMap<String, String>>builder()
                .url(InicisApiConstants.VIRTUAL_ACCOUNT_SEQ_URL)
                .mediaType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(ObjectMapperUtil.convertMultiValueMap(vacctSeqReqVO)).build();

        return restTemplateUtil.callApiByPost(restTemplateReqVO, VacctSeqResVO.class);
    }

    public VacctCnlResVO callVacctCnl(){
        VacctCnlReqVO vacctCnlReqVO = inicisApiReqMaker.makeVacctCnlReqVO();

        RestTemplateReqVO<MultiValueMap<String, String>> restTemplateReqVO = RestTemplateReqVO.<MultiValueMap<String, String>>builder()
                .url(InicisApiConstants.VIRTUAL_ACCOUNT_REFUND)
                .mediaType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(ObjectMapperUtil.convertMultiValueMap(vacctCnlReqVO)).build();

        return restTemplateUtil.callApiByPost(restTemplateReqVO, VacctCnlResVO.class);
    }


}
