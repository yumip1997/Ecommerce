package com.plateer.ec1.promotion.point.vo;

import com.plateer.ec1.common.model.promotion.CcMbrPntModel;
import com.plateer.ec1.promotion.point.validation.groups.PointRestore;
import com.plateer.ec1.promotion.point.validation.groups.PointSave;
import com.plateer.ec1.promotion.point.validation.groups.PointUse;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointVO {

    private long pntHstSeq;
    @NotNull(groups = {PointSave.class, PointUse.class,PointRestore.class})
    private String mbrNo;
    @NotNull(groups = {PointSave.class, PointUse.class,PointRestore.class})
    private String svUseCcd;
    @NotNull(groups = {PointSave.class, PointUse.class,PointRestore.class})
    private Long svUseAmt;
    private Long pntBlc;
    @NotNull(groups = {PointUse.class, PointRestore.class})
    private String ordNo;
    @NotNull(groups = {PointUse.class, PointRestore.class})
    private String payNo;

    public CcMbrPntModel toCcMbrPntModel(Long pntBlc){
        CcMbrPntModel ccMbrPntModel = new CcMbrPntModel();
        BeanUtils.copyProperties(this, ccMbrPntModel);
        ccMbrPntModel.setPntBlc(pntBlc);
        return ccMbrPntModel;
    }

}
