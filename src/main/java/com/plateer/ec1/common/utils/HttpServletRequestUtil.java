package com.plateer.ec1.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class HttpServletRequestUtil {

    public static String getClientIP(HttpServletRequest request) {
        List<String> headerNames = Arrays.asList("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");

        for (String headerName : headerNames) {
            String header = request.getHeader(headerName);

            if(StringUtils.isNotEmpty(header)){
                return header;
            }
        }

        return request.getRemoteAddr();
    }

}
