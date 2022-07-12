package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.factory.PaymentServiceFactory;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.req.CancelReqVO;
import com.plateer.ec1.payment.vo.req.NetCancelReqVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PaymentServiceFactory paymentServiceFactory;

    public List<ApproveResVO> approve(OrderInfoVO orderInfoVO, List<PayInfoVO> payInfoVOList){
        List<ApproveResVO> approveResVOList = new ArrayList<>();

        for (PayInfoVO payInfoVO : payInfoVOList) {
            PaymentService paymentService = paymentServiceFactory.get(payInfoVO.getPaymentType());
            ApproveResVO approveResVO = paymentService.approvePay(orderInfoVO, payInfoVO);
            approveResVOList.add(approveResVO);
        }

        return approveResVOList;
    }

    public void cancel(CancelReqVO cancelReqVO){
        PaymentService paymentService = paymentServiceFactory.get(cancelReqVO.getPaymentType());
        paymentService.cancelPay(cancelReqVO.getOriginOrderVO());
    }

    public void netCancel(NetCancelReqVO netCancelReqVO){
        PaymentService paymentService = paymentServiceFactory.get(netCancelReqVO.getPaymentType());
        paymentService.netCancel(netCancelReqVO);
    }

}
