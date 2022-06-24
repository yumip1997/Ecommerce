package com.plateer.ec1.claim.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogDto<T, U>{

    private T insertData;
    private U updateData;

}
