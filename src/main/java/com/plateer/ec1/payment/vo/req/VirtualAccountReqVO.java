package com.plateer.ec1.payment.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.utils.EncryptUtil;
import com.plateer.ec1.payment.utils.InicisConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
public class VirtualAccountReqVO{

    @Builder.Default
    private String type = InicisConstants.PAYMENT_TYPE;
    @Builder.Default
    private String paymethod = InicisConstants.PAYMENT_VACCT;
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

    public void setUpVirtualAccountReqVO(String INIAPI_KEY){
        this.setNow(LocalDateTime.now());
        this.setTimestamp(makeTimestamp());
        this.setClientIp(makeClientIp());
        this.setDtInput(makeDtInput());
        this.setTmInput(makeTmInput());
        this.setHashData(makeHashData(INIAPI_KEY));
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

    public String makeHashData(String INIAPI_KEY){
        StringBuilder input = new StringBuilder();
        input.append(INIAPI_KEY)
                .append(this.getType())
                .append(this.getPaymethod())
                .append(this.getTimestamp())
                .append(this.getClientIp())
                .append(this.getMid())
                .append(this.getMoid())
                .append(this.getPrice());

        return EncryptUtil.encryptSha521(input.toString());
    }

}
