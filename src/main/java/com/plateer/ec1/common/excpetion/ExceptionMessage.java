package com.plateer.ec1.common.excpetion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessage {

    NOT_VALID_CODE("코드 유형이 올바르지 않습니다!");

    public final String msg;
}
