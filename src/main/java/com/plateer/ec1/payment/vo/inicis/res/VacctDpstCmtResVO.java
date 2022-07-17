package com.plateer.ec1.payment.vo.inicis.res;

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
public class VacctDpstCmtResVO implements ValidResVO {

    private String no_tid;
    private String no_oid;
    private String cd_bank;
    private String cd_deal;
    private String dt_trans;
    private String tm_trans;
    private String no_vacct;
    private String amt_input;
    private String flg_close;
    private String cl_close;
    private String type_msg;
    private String nm_inputbank;
    private String nm_input;
    private String dt_inputstd;
    private String dt_calculstd;
    private String dt_transbase;
    private String cl_trans;
    private String cl_kor;
    private String dt_cshr;
    private String tm_cshr;
    private String no_cshr_appl;
    private String no_cshr_tid;
    private String no_req_tid;

    @Override
    public void isValidRes(){
        if(!PaymentBusiness.VACCT_DPST_CMT.getSucessCode().equals(this.getType_msg())){
            throw new BusinessException(PaymentException.FAIL_VACCT_DEPOSIT.MSG);
        }
    }

}
