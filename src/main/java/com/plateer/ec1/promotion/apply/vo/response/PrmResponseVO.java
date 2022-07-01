package com.plateer.ec1.promotion.apply.vo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrmResponseVO<T> extends ResponseBaseVO {

    private List<T> list;

    @Builder
    public PrmResponseVO(List<T> list, String mbrNo) {
        super(mbrNo);
        this.list = list;
    }

}
