package com.plateer.ec1.payment.vo.inicis.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.utils.LocalDateTimeUtil;
import lombok.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InicisReqBase {

    private String type;
    private String paymethod;
    private String timestamp;
    private String clientIp;
    private String mid;

    @JsonIgnore
    protected LocalDateTime now;

    public InicisReqBase(String type, String paymethod){
        this.type = type;
        this.paymethod = paymethod;
    }

    public String makeTimestamp(){
        return LocalDateTimeUtil.toStringYearToSeconds(now);
    }

    public String makeClientIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public void setUp(String MID){
        this.setMid(MID);
        this.setNow(LocalDateTime.now());
        this.setTimestamp(makeTimestamp());
        this.setClientIp(makeClientIp());
    }
}
