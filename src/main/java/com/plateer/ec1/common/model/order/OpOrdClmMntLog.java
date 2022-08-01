package com.plateer.ec1.common.model.order;

import com.plateer.ec1.common.model.BaseModel;
import com.plateer.ec1.common.utils.ObjectMapperUtil;
import com.plateer.ec1.order.enums.OPT0012Code;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpOrdClmMntLog extends BaseModel {

    private Long logSeq;
    private String ordNo;
    private String clmNo;
    private String reqPram;
    private String insData;
    private String uptData;
    private String procCcd;

    public static OpOrdClmMntLog getInsertData(OrderClaimBaseVO baseVO){
        return OpOrdClmMntLog.builder()
                .ordNo(baseVO.getOrdNo())
                .clmNo(baseVO.getClmNo())
                .reqPram(ObjectMapperUtil.toJson(baseVO))
                .build();
    }

    public static <T, U> OpOrdClmMntLog getUpdateData(Long logSeq, OrdClmCreationVO<T,U> creationVO){
        return OpOrdClmMntLog.builder()
                .logSeq(logSeq)
                .ordNo(creationVO.getOrdNo())
                .clmNo(creationVO.getClmNo())
                .insData(ObjectMapperUtil.toJson(creationVO.getInsertData()))
                .uptData(ObjectMapperUtil.toJson(creationVO.getUpdateData()))
                .procCcd(OPT0012Code.getCodeByException(creationVO.getException()))
                .build();

    }

}
