package com.plateer.ec1.payment.vo.inicis.res;

import com.plateer.ec1.common.validation.annotation.ResValid;
import com.plateer.ec1.common.vo.ValidResVO;
import com.plateer.ec1.payment.enums.PaymentBusiness;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ResValid
public class VacctCnlResVO implements ValidResVO {

    private String resultCode;
    private String resultMsg;
    private String cancelDate;
    private String cancelTime;
    private String cshrCancelNum;
    private String detailResultCode;
    private String receiptInfo;
    private String tid;
    private String prtcTid;
    private String prtcPrice;
    private String prtcRemains;
    private String prtcCnt;


    @Override
    public boolean isValidRes() {
        return PaymentBusiness.VACCT_CNL.getSucessCode().equals(this.resultCode);
    }
}
