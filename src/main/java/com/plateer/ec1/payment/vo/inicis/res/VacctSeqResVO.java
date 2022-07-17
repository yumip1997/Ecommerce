package com.plateer.ec1.payment.vo.inicis.res;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.vo.ValidResVO;
import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.enums.PaymentException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VacctSeqResVO implements ValidResVO {

    private String resultCode;
    private String resultMsg;
    private String tid;
    private String authDate;
    private String authTime;
    private String vacct;
    private String vacctName;
    private String vacctBankCode;
    private String validDate;
    private String validTime;
    private String price;
    private String ablePartialCancelYn;

    @Override
    public void isValidRes() {
        if(!PaymentBusiness.VACCT_APV.getSucessCode().equals(this.resultCode)){
            throw new BusinessException(PaymentException.FAIL_APPROVE.MSG);
        }
    }
}
