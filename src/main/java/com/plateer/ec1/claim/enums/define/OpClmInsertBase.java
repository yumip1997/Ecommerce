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
public enum OpClmInsertBase {

    //일반상품주문취소완료
    GCC(toList(CANCEL.code), toList(OPT0004Code.CANCEL_COMPLETE.code), toList(DELIVERY.code), IntUnaryOperator.identity(), TRUE, LocalDateTime::now),
    //주문취소접수
    CA(toList(CANCEL.code), toList(OPT0004Code.CANCEL_REQUEST.code), toList(DELIVERY.code), IntUnaryOperator.identity(), TRUE, () -> null),
    //문취소완료
    CC(toList(CANCEL.code), toList(OPT0004Code.CANCEL_COMPLETE.code), toList(DELIVERY.code), IntUnaryOperator.identity(), FALSE, LocalDateTime::now),

    //반품접수
    RA(toList(RETURN_ACCEPT.code), toList(OPT0004Code.RETURN_ACCEPT.code), toList(RETRIEVE.code), (dvp) -> dvp + 1, TRUE, () -> null),
    //반품철회
    RW(toList(RETURN_WITHDRAWAL.code), toList(OPT0004Code.RETURN_WITHDRAWAL.code), toList(RETRIEVE.code), IntUnaryOperator.identity(), TRUE, LocalDateTime::now),

    //교환접수
    EA(toList(EXCHANGE_ACCEPT.code, EXCHANGE_ACCEPT.code), toList(OPT0004Code.EXCHANGE_ACCEPT.code, OPT0004Code.EXCHANGE_ACCEPT.code),
            toList(RETRIEVE.code, DELIVERY.code), (dvp) -> dvp + 1, TRUE, () -> null),
    //교환철회
    EW(toList(RETURN_WITHDRAWAL.code, CANCEL.code), toList(OPT0004Code.RETURN_WITHDRAWAL.code, OPT0004Code.CANCEL_COMPLETE.code),
            toList(RETRIEVE.code, DELIVERY.code), IntUnaryOperator.identity(), TRUE, LocalDateTime::now);

    private final List<String> ordClmTpCd;
    private final List<String> ordPrgsScd;
    private final List<String> dvRctCcdList;
    private final IntUnaryOperator dvpGrpOperator;
    private final boolean createClaimNoFlag;
    private final Supplier<LocalDateTime> cmtDtimeSupplier;

    private static List<String> toList(String... codes) {
        return Arrays.asList(codes);
    }


}
