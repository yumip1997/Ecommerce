package com.plateer.ec1.common.log;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TraceStatus {

    private final TraceId traceId;
    private final long startTimeMs;
    private final String message;

}
