package com.plateer.ec1.common.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

@Getter
@Setter
@Builder
public class RestTemplateReqVO<T> {

    private String url;
    private MediaType mediaType;
    private T body;

}
