package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import static com.plateer.ec1.order.enums.OPT0003Code.*;
import static com.plateer.ec1.order.enums.OPT0013Code.DELIVERY;
import static com.plateer.ec1.order.enums.OPT0013Code.RETRIEVE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Getter
public enum OpClmBase {

    //일반상품주문취소완료
    GCC(CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, toList(DELIVERY.code), IntUnaryOperator.identity(), TRUE, LocalDateTime::now),
    //모바일쿠폰주문취소접수
    MCA(CANCEL.code, OPT0004Code.CANCEL_REQUEST.code, toList(DELIVERY.code), IntUnaryOperator.identity(), TRUE, () -> null),
    //모바일쿠폰주문취소완료
    MCC(CANCEL.code, OPT0004Code.CANCEL_COMPLETE.code, toList(DELIVERY.code), IntUnaryOperator.identity(), FALSE, LocalDateTime::now),

    //반품접수
    RA(RETURN_ACCEPT.code, OPT0004Code.RETURN_ACCEPT.code, toList(RETRIEVE.code), (dvp) -> dvp + 1, TRUE, () -> null),
    //반품철회
    RW(RETURN_WITHDRAWAL.code, OPT0004Code.RETURN_WITHDRAWAL.code, toList(RETRIEVE.code), IntUnaryOperator.identity(), TRUE, LocalDateTime::now),

    //교환접수
    EA(EXCHANGE_ACCEPT.code, OPT0004Code.EXCHANGE_ACCEPT.code, toList(DELIVERY.code, RETRIEVE.code), (dvp) -> dvp + 1, TRUE, () -> null),
    //교환철회
    EW(EXCHANGE_WITHDRAWAL.code, OPT0004Code.EXCHANGE_WITHDRAWAL.code, toList(DELIVERY.code, RETRIEVE.code), IntUnaryOperator.identity(), TRUE, LocalDateTime::now);

    private final String ordClmTpCd;
    private final String ordPrgsScd;
    private final List<String> dvRctCcdList;
    private final IntUnaryOperator dvpGrpOperator;
    private final boolean createClaimNoFlag;
    private final Supplier<LocalDateTime> cmtDtimeSupplier;

    private static List<String> toList(String... codes) {
        return Arrays.asList(codes);
    }


}
