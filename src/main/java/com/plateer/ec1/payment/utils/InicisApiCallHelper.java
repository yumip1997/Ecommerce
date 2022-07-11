package com.plateer.ec1.payment.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plateer.ec1.payment.vo.req.VirtualAccountReqVO;
import com.plateer.ec1.payment.vo.res.VirtualAccountResVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class InicisApiCallHelper {

    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    public VirtualAccountResVO callVirtualAccountNum(VirtualAccountReqVO virtualAccountReqVO){
        Map<String, String> stringStringMap = objectMapper.convertValue(virtualAccountReqVO, new TypeReference<Map<String, String>>() {});

        ResponseEntity<VirtualAccountResVO> virtualAccountResVOResponseEntity =
                restTemplate
                        .postForEntity(InicisApiConstants.VIRTUAL_ACCOUNT_NUM_REQEUST_URL, stringStringMap, VirtualAccountResVO.class);

        return null;
    }


}
