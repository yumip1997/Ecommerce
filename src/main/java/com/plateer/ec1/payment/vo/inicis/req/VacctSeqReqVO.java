package com.plateer.ec1.payment.vo.inicis.req;

import com.plateer.ec1.common.utils.CipherUtil;
import com.plateer.ec1.common.utils.LocalDateTimeUtil;
import com.plateer.ec1.payment.utils.inicis.InicisApiConstants;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacctSeqReqVO extends InicisReqBase{

    private String url;
    private String moid;
    private String goodName;
    private String buyerName;
    private String buyerEmail;
    private String buyerTel;
    private String price;
    private String currency;
    private String bankCode;
    private String dtInput;
    private String tmInput;
    private String nmInput;
    private String flgCash;
    private String cashRegNo;
    private String vacctType;
    private String vacct;
    private String hashData;

    public VacctSeqReqVO(String type, String paymethod){
        super(type, paymethod);
    }

    public static VacctSeqReqVO of(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO){
        VacctSeqReqVO reqVO = new VacctSeqReqVO(InicisApiConstants.TYPE_PAY, InicisApiConstants.PAYMETHOD_VACCT);
        reqVO.setMoid(orderInfoVO.getOrdNo());
        reqVO.setGoodName(orderInfoVO.getGoodName());
        reqVO.setBuyerName(orderInfoVO.getBuyerName());
        reqVO.setBuyerEmail(orderInfoVO.getBuyerEmail());
        reqVO.setPrice(String.valueOf(payInfoVO.getPayAmount()));
        reqVO.setBankCode(payInfoVO.getBankCode());
        reqVO.setNmInput(payInfoVO.getDepositorName());
        return reqVO;
    }

    public void setUpVirtualAccountReqVO(String API_KEY, String MID){
        super.setUp(MID);
        this.setDtInput(makeDtInput());
        this.setTmInput(makeTmInput());
        this.setHashData(makeHashData(API_KEY));
    }

    public String makeDtInput(){
        return LocalDateTimeUtil.toStringYearToDate(now.plusDays(1));
    }

    public String makeTmInput(){
        return LocalDateTimeUtil.toStringHourToSeconds(now.plusDays(1));
    }

    public String makeHashData(String API_KEY){
        String input = new StringBuilder()
                .append(API_KEY)
                .append(this.getType())
                .append(this.getPaymethod())
                .append(this.getTimestamp())
                .append(this.getClientIp())
                .append(this.getMid())
                .append(this.getMoid())
                .append(this.getPrice())
                .toString();

        return CipherUtil.encrypt(input);
    }
}
