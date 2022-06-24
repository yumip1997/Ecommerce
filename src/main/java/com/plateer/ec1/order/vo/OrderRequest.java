package com.plateer.ec1.order.vo;

import com.plateer.ec1.payment.vo.PayInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequest {

    String orderNo;
    String systemType;
    String orderType;
    PayInfo payInfo;

}
