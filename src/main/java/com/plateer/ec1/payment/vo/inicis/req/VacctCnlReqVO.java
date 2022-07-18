package com.plateer.ec1.payment.vo.inicis.req;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.common.utils.CipherUtil;
import com.plateer.ec1.payment.utils.inicis.InicisApiConstants;
import com.plateer.ec1.payment.vo.OriginOrderVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacctCnlReqVO extends InicisReqBase{

    private String tid;
    private String msg;
    private String price;
    private String refundAcctNum;
    private String refundBankCode;
    private String refundAcctName;
    private String hashData;

    public VacctCnlReqVO(String type, String paymethod){
        super(type, paymethod);
    }

    public static VacctCnlReqVO of(String type, OpPayInfoModel payInfoModel){
        VacctCnlReqVO reqVO = new VacctCnlReqVO(type, InicisApiConstants.PAYMETHOD_VACCT);
        //TODO 암호화
        reqVO.setRefundAcctNum(payInfoModel.getVrAcct());
        reqVO.setRefundBankCode(payInfoModel.getVrBnkCd());
        reqVO.setRefundAcctName(payInfoModel.getVrAcctNm());
        return reqVO;
    }

    public void setUpVacctCnlReqVO(String MID, String API_KEY){
        super.setUp(MID);
        this.setHashData(API_KEY);
    }

    public String makeHashData(String API_KEY){
        String input = super.getBasicHashData(API_KEY)
                .append(API_KEY)
                .append(this.getType())
                .append(this.getType())
                .toString();

        return CipherUtil.encrypt(input);
    }


}
