package com.plateer.ec1.common.model.promotion;

import com.plateer.ec1.common.vo.BaseVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CcCpnIssueModel extends BaseVO {

    private Long cpnIssNo;
    private String mbrNo;
    private LocalDateTime cpnUseDt;
    private Long orgCpnIssNo;
    private String cpnCertNo;
    private String ordNo;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;
    private Long prmNo;

    public static <T> CcCpnIssueModel convertModel(T t){
        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.builder().build();
        BeanUtils.copyProperties(t, ccCpnIssueModel);
        return ccCpnIssueModel;
    }

}
