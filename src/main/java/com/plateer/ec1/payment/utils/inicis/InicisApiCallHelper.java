package com.plateer.ec1.payment.utils.inicis;

import com.plateer.ec1.common.utils.ObjectMapperUtil;
import com.plateer.ec1.common.utils.RestTemplateUtil;
import com.plateer.ec1.common.vo.RestTemplateReqVO;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.inicis.req.VacctCnlReqVO;
import com.plateer.ec1.payment.vo.inicis.req.VacctSeqReqVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctCnlResVO;
import com.plateer.ec1.payment.vo.inicis.res.VacctSeqResVO;
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
        RestTemplateReqVO<MultiValueMap<String, String>> restTemplateReqVO = getRestTemplateReqVO(InicisApiConstants.VIRTUAL_ACCOUNT_SEQ_URL, vacctSeqReqVO);

        return restTemplateUtil.callApiByPost(restTemplateReqVO, VacctSeqResVO.class);
    }

    public VacctCnlResVO callVacctCnl(OrderPayInfoVO orderPayInfoVO){
        String type = orderPayInfoVO.isPartialCancel() ? InicisApiConstants.TYPE_PARTIAL_REFUND : InicisApiConstants.TYPE_REFUND;
        VacctCnlReqVO vacctCnlReqVO = inicisApiReqMaker.makeVacctCnlReqVO(type, orderPayInfoVO);
        RestTemplateReqVO<MultiValueMap<String, String>> restTemplateReqVO = getRestTemplateReqVO(InicisApiConstants.VIRTUAL_ACCOUNT_REFUND, vacctCnlReqVO);

        return restTemplateUtil.callApiByPost(restTemplateReqVO, VacctCnlResVO.class);
    }

    private <T> RestTemplateReqVO<MultiValueMap<String, String>> getRestTemplateReqVO(String uri, T body){
        return RestTemplateReqVO.<MultiValueMap<String, String>>builder()
                .url(uri)
                .mediaType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(ObjectMapperUtil.convertMultiValueMap(body))
                .build();
    }
}
