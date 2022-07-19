package com.plateer.ec1.payment.vo.inicis.req;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.common.utils.CipherUtil;
import com.plateer.ec1.payment.utils.inicis.InicisApiConstants;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacctCnlReqVO extends InicisReqBase{

    private String tid;
    private String msg;
    private String price;
    private String confirmPrice;
    private String refundAcctNum;
    private String refundBankCode;
    private String refundAcctName;
    private String hashData;

    public VacctCnlReqVO(String type, String paymethod){
        super(type, paymethod);
    }

    public static VacctCnlReqVO of(String type, OrderPayInfoVO orderPayInfoVO){
        VacctCnlReqVO reqVO = new VacctCnlReqVO(type, InicisApiConstants.PAYMETHOD_VACCT);
        reqVO.setTid(orderPayInfoVO.getTrsnId());
        //TODO 금액 셋팅 수정 필요
        reqVO.setPrice(String.valueOf(orderPayInfoVO.getCnclAmt()));
        reqVO.setConfirmPrice(String.valueOf(orderPayInfoVO.getRfndAvlAmt()));
        reqVO.setRefundAcctNum(orderPayInfoVO.getRfndAcctNo());
        reqVO.setRefundBankCode(orderPayInfoVO.getRfndBnkCk());
        reqVO.setRefundAcctName(orderPayInfoVO.getRfndAcctOwnNm());
        return reqVO;
    }

    public void setUpVacctCnlReqVO(String MID, String API_KEY, String IV){
        super.setUp(MID, API_KEY);
        String hashData = InicisApiConstants.TYPE_REFUND.equals(this.getType()) ? getAllCancelHashData(API_KEY, IV) : getPartialRefundHashData(API_KEY, IV);
        this.setHashData(hashData);
    }

    public String getAllCancelHashData(String API_KEY, String IV){
        String input = super.getBasicHashData()
                .append(this.getTid())
                .append(this.getRefundAcctNum())
                .toString();

        return CipherUtil.encryptByAES(input, API_KEY, IV);
    }

    public String getPartialRefundHashData(String API_KEY, String IV){
        String input = super.getBasicHashData()
                .append(this.getTid())
                .append(this.getPrice())
                .append(this.getConfirmPrice())
                .append(this.getRefundAcctNum())
                .toString();

        return CipherUtil.encryptByAES(input, API_KEY, IV);
    }


}
