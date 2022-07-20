package com.plateer.ec1.dao.payment;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.payment.mapper.PaymentMapper;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import com.plateer.ec1.resource.TestConstants;

public class PaymentMockMapper implements PaymentMapper {

    private JsonReaderUtil jsonReaderUtil;

    public PaymentMockMapper(){
        this.jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "payment");
    }

    @Override
    public OrderPayInfoVO getOrderPayInfo(PaymentCancelReqVO reqVO) {
        OrderPayInfoVO object = jsonReaderUtil.getObject("/CancelResDummy.json", OrderPayInfoVO.class);
        object.setCnclReqAmt(reqVO.getCnclReqAmt());
        return object;
    }
}
