package com.plateer.ec1.payment.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.utils.CipherUtil;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacctSeqReqVO {

    private static final String PAYMENT_TYPE ="Pay";
    private static final String PAYMENT_VACCT ="Vacct";

    @Builder.Default
    private String type = PAYMENT_TYPE;
    @Builder.Default
    private String paymethod = PAYMENT_VACCT;
    private String timestamp;
    private String clientIp;
    private String mid;
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

    @JsonIgnore
    private LocalDateTime now;

    public static VacctSeqReqVO of(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO){
        return VacctSeqReqVO
                .builder()
                .moid(orderInfoVO.getOrdNo())
                .goodName(orderInfoVO.getGoodName())
                .buyerName(orderInfoVO.getBuyerName())
                .buyerEmail(orderInfoVO.getBuyerEmail())
                .price(String.valueOf(payInfoVO.getPayAmount()))
                .bankCode(payInfoVO.getBankCode())
                .nmInput(payInfoVO.getDepositorName())
                .build();
    }

    public void setUpVirtualAccountReqVO(String API_KEY, String MID){
        this.setMid(MID);
        this.setNow(LocalDateTime.now());
        this.setTimestamp(makeTimestamp());
        this.setClientIp(makeClientIp());
        this.setDtInput(makeDtInput());
        this.setTmInput(makeTmInput());
        this.setHashData(makeHashData(API_KEY));
    }

    public String makeTimestamp(){
        return now.format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"));
    }

    public String makeClientIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public String makeDtInput(){
        return now.plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public String makeTmInput(){
        return now.plusDays(1).format(DateTimeFormatter.ofPattern("hhmm"));
    }

    public String makeHashData(String API_KEY){
        StringBuilder input = new StringBuilder();
        input.append(API_KEY)
                .append(this.getType())
                .append(this.getPaymethod())
                .append(this.getTimestamp())
                .append(this.getClientIp())
                .append(this.getMid())
                .append(this.getMoid())
                .append(this.getPrice());

        return CipherUtil.encrypt(input.toString());
    }
}
