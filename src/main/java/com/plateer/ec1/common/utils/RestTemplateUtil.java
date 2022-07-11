package com.plateer.ec1.common.utils;

import com.plateer.ec1.common.vo.RestTemplateReqVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestTemplateUtil {

    private final RestTemplate restTemplate;

    public <T, U> U callApiByPost(RestTemplateReqVO<T> restTemplateReqVO, Class<U> returnType)  {

        RequestEntity<T> requestEntity= RequestEntity.post(restTemplateReqVO.getUrl())
                .contentType(restTemplateReqVO.getMediaType())
                .body(restTemplateReqVO.getBody());

        String result = restTemplate.exchange(requestEntity, String.class).getBody();

        return ObjectMapperUtil.convertJsonIntoObject(result, returnType);
    }
}
