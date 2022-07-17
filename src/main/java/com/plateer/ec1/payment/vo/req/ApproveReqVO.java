package com.plateer.ec1.payment.vo.req;

import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
public class ApproveReqVO {

    @Valid
    private OrderInfoVO orderInfoVO;

    @Valid
    @NotEmpty
    private List<PayInfoVO> payInfoVOList;

}
