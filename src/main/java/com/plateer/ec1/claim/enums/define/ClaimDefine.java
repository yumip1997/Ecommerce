package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.claim.enums.*;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ClaimDefine {

    //일반상품주문취소완료
    GCC(ProcessorType.COMPLETE, ValidatorType.COMPLETE, ValidStatus.GCC, Boolean.TRUE, OpClmBase.GCC),
    //모바일쿠폰주문취소접수
    MCA(ProcessorType.ACCEPT_WITHDRAWAL, ValidatorType.ACCEPT_WITHDRAWAL, ValidStatus.MCA, Boolean.TRUE, OpClmBase.MCA),
    //모바일쿠폰주문취소완료
    MCC(ProcessorType.COMPLETE, ValidatorType.COMPLETE, ValidStatus.MCC, Boolean.FALSE, OpClmBase.MCC),

    //반품접수
    RA(ProcessorType.ACCEPT_WITHDRAWAL, ValidatorType.ACCEPT_WITHDRAWAL, ValidStatus.RA, Boolean.TRUE, OpClmBase.RA),
    //반품철회
    RW(ProcessorType.ACCEPT_WITHDRAWAL, ValidatorType.ACCEPT_WITHDRAWAL, ValidStatus.RW, Boolean.TRUE, OpClmBase.RW),

    //교환접수
    EA(ProcessorType.ACCEPT_WITHDRAWAL, ValidatorType.ACCEPT_WITHDRAWAL, ValidStatus.EA, Boolean.TRUE, OpClmBase.EA),
    //교환철회
    EW(ProcessorType.ACCEPT_WITHDRAWAL, ValidatorType.ACCEPT_WITHDRAWAL, ValidStatus.EW, Boolean.TRUE, OpClmBase.EW);

    private final ProcessorType processorType;
    private final ValidatorType validatorType;
    private final ValidStatus validStatus;
    private final Boolean numFlag;
    private final OpClmBase opClmBase;

    //TODO find 로직 수정 필요!!
    public static ProcessorType findClaimProcessorType(ClaimBaseVO ClaimBaseVO){
        try{
            return ClaimDefine.valueOf(ClaimBaseVO.getClaimType()).getProcessorType();
        }catch (Exception e){
            throw new IllegalArgumentException(ClaimException.INVALID_CLAIM_TYPE.EXCEPTION_MSG);
        }
    }

    public static ValidatorType findClaimValidatorType(ClaimBaseVO ClaimBaseVO){
        try{
            return ClaimDefine.valueOf(ClaimBaseVO.getClaimType()).getValidatorType();
        }catch (Exception e){
            throw new IllegalArgumentException(ClaimException.INVALID_CLAIM_TYPE.EXCEPTION_MSG);
        }
    }

    public static ValidStatus findClaimValidStatusCode(ClaimBaseVO ClaimBaseVO){
        try{
            return ClaimDefine.valueOf(ClaimBaseVO.getClaimType()).getValidStatus();
        }catch (Exception e){
            throw new IllegalArgumentException(ClaimException.INVALID_CLAIM_TYPE.EXCEPTION_MSG);
        }
    }

    public static Boolean findNumFlag(ClaimBaseVO ClaimBaseVO){
        try{
            return ClaimDefine.valueOf(ClaimBaseVO.getClaimType()).getNumFlag();
        }catch (Exception e){
            throw new IllegalArgumentException(ClaimException.INVALID_CLAIM_TYPE.EXCEPTION_MSG);
        }

    }

    public static OpClmBase findOrderClaimBaseCode(ClaimBaseVO ClaimBaseVO){
        try {
            return ClaimDefine.valueOf(ClaimBaseVO.getClaimType()).getOpClmBase();
        }catch (Exception e){
            throw new IllegalArgumentException(ClaimException.INVALID_CLAIM_TYPE.EXCEPTION_MSG);
        }
    }

}
