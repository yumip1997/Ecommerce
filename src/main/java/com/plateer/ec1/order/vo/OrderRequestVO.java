package com.plateer.ec1.order.vo;

import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRequestVO {

    String orderNo;
    String systemType;
    String orderType;
    PayInfoVO payInfoVO;

}
