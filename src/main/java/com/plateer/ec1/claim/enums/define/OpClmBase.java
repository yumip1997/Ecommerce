package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.enums.OPT0013Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public enum OpClmBase {

    //일반상품주문취소완료
    GCC(OPT0003Code.CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code,
            toList(OPT0013Code.DELIVERY.code), IntUnaryOperator.identity(), Boolean.TRUE, LocalDateTime::now),
    //모바일쿠폰주문취소접수
    MCA(OPT0003Code.CANCEL.code, OPT0004Code.CANCEL_REQUEST.code,
            toList(OPT0013Code.DELIVERY.code), IntUnaryOperator.identity(), Boolean.TRUE, () -> null),
    //모바일쿠폰주문취소완료
    MCC(OPT0003Code.CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code,
            toList(OPT0013Code.DELIVERY.code), IntUnaryOperator.identity(), Boolean.FALSE,LocalDateTime::now),

    //반품접수
    RA(OPT0003Code.RETURN_ACCEPT.code, OPT0004Code.RETURN_ACCEPT.code,
            toList(OPT0013Code.RETRIEVE.code), (dvp) -> dvp+1, Boolean.TRUE, () -> null),
    //반품철회
    RW(OPT0003Code.RETURN_WITHDRAWAL.code, OPT0004Code.RETURN_WITHDRAWAL.code,
            toList(OPT0013Code.RETRIEVE.code), IntUnaryOperator.identity(), Boolean.TRUE, LocalDateTime::now),

    //교환접수
    EA(OPT0003Code.EXCHANGE_ACCEPT.code, OPT0004Code.EXCHANGE_ACCEPT.code,
            toList(OPT0013Code.DELIVERY.code, OPT0013Code.RETRIEVE.code), (dvp) -> dvp+1,  Boolean.TRUE, () -> null),
    //교환철회
    EW(OPT0003Code.EXCHANGE_WITHDRAWAL.code, OPT0004Code.EXCHANGE_WITHDRAWAL.code,
            toList(OPT0013Code.DELIVERY.code, OPT0013Code.RETRIEVE.code), IntUnaryOperator.identity(), Boolean.TRUE, LocalDateTime::now);

    private final String ordClmTpCd;
    private final String ordPrgsScd;
    private final List<String> dvRctCcdList;
    private final IntUnaryOperator dvpGrpOperator;
    private final boolean createClaimNoFlag;
    private final Supplier<LocalDateTime> cmtDtimeSupplier;

    private static List<String> toList(String ...codes){
        return Arrays.asList(codes);
    }



}
