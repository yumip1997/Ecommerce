package com.plateer.ec1.payment.utils;

import com.plateer.ec1.common.utils.ObjectMapperUtil;
import com.plateer.ec1.common.utils.RestTemplateUtil;
import com.plateer.ec1.common.vo.RestTemplateReqVO;
import com.plateer.ec1.payment.vo.req.VirtualAccountReqVO;
import com.plateer.ec1.payment.vo.res.VirtualAccountResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
@Component
public class InicisApiCallHelper {

    private final RestTemplateUtil restTemplateUtil;

    public VirtualAccountResVO callVirtualAccountSeq(VirtualAccountReqVO virtualAccountReqVO) {

        RestTemplateReqVO<MultiValueMap<String, String>> restTemplateReqVO = RestTemplateReqVO.<MultiValueMap<String, String>>builder()
                .url(InicisApiConstants.VIRTUAL_ACCOUNT_SEQ_URL)
                .mediaType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(ObjectMapperUtil.convertMultiValueMap(virtualAccountReqVO)).build();

        VirtualAccountResVO virtualAccountResVO = restTemplateUtil.callApiByPost(restTemplateReqVO, VirtualAccountResVO.class);

        return virtualAccountResVO;
    }


}
