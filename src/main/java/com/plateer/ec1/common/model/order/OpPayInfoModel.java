package com.plateer.ec1.common.model.order;

import com.plateer.ec1.common.model.BaseModel;
import com.plateer.ec1.payment.enums.OPT0009Code;
import com.plateer.ec1.payment.enums.OPT0010Code;
import com.plateer.ec1.payment.enums.OPT0011Code;
import com.plateer.ec1.payment.vo.req.VacctSeqReqVO;
import com.plateer.ec1.payment.vo.res.VacctSeqResVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
public class OpPayInfoModel extends BaseModel {

    private String payNo;
    private String ordNo;
    private String clmNo;
    private String payMnCd;
    private String payCcd;
    private String payPrgsScd;
    private Long payAmt;
    private Long cnclAmt;
    private Long rfndAvlAmt;
    private String trsnId;
    private LocalDateTime payCmtDtime;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private String orgPayNo;
    private String vrAcct;
    private String vrAcctNm;
    private String vrBnkCd;
    private String vrValDt;
    private String vrValTt;

    public static OpPayInfoModel getInsertData(VacctSeqReqVO reqVO, VacctSeqResVO resVO){
        return OpPayInfoModel.builder()
                .ordNo(reqVO.getMoid())
                .payMnCd(OPT0009Code.VIRTUAL_ACCOUNT.getCode())
                .payCcd(OPT0010Code.PAY.getCode())
                .payPrgsScd(OPT0011Code.PAY_REQUEST.getCode())
                .payAmt(Long.parseLong(reqVO.getPrice()))
                .cnclAmt(0L)
                .rfndAvlAmt(Long.parseLong(reqVO.getPrice()))
                .trsnId(resVO.getTid())
                .payCmtDtime(LocalDateTime.parse(resVO.getAuthDate() + resVO.getAuthTime(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .vrAcct(resVO.getVacct())
                .vrBnkCd(resVO.getVacctBankCode())
                .vrAcctNm(resVO.getVacctName())
                .vrBnkCd(resVO.getVacctBankCode())
                .vrValDt(resVO.getValidDate())
                .vrValTt(resVO.getValidTime())
                .build();
    }

}
