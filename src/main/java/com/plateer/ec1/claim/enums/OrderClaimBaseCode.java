package com.plateer.ec1.claim.enums;

import com.plateer.ec1.common.model.order.OrderClaim;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum OrderClaimBaseCode {

    //일반상품주문취소완료
    GCC(Arrays.asList(OrderClaim.builder().ordClmTpCd("C").ordPrgsScd(OrdPrgsScdCode.ORDER_COMPLETE.code).build()),
            Collections.emptyList()
    ),
    //모바일쿠폰주문취소접수
    MCA(Arrays.asList(OrderClaim.builder().ordClmTpCd("C").ordPrgsScd(OrdPrgsScdCode.CANCEL_REQUEST.code).build()),
            Collections.emptyList()
    ),
    //모바일쿠폰주문취소완료
    MCC(Collections.emptyList(),
            Arrays.asList(OrderClaim.builder().ordPrgsScd(OrdPrgsScdCode.CANCEL_COMPLETE.code).build())
    ),

    //반품접수
    RA(Arrays.asList(OrderClaim.builder().ordClmTpCd("R").dvRvtCcd("R").ordPrgsScd(OrdPrgsScdCode.RETURN_ACCEPT.code).build()),
            Collections.emptyList()
    ),
    //반품철회
    RW(Arrays.asList(OrderClaim.builder().ordClmTpCd("R").dvRvtCcd("D").ordPrgsScd(OrdPrgsScdCode.RETURN_WITHDRAWAL.code).build()),
            Collections.emptyList()
    ),

    //교환접수
    EA(
            Arrays.asList(
                    OrderClaim.builder().ordClmTpCd("X").dvRvtCcd("R").ordPrgsScd(OrdPrgsScdCode.EXCHANGE_ACCEPT.code).build(),
                    OrderClaim.builder().ordClmTpCd("X").dvRvtCcd("D").ordPrgsScd(OrdPrgsScdCode.EXCHANGE_ACCEPT.code).build()
            ),
            Collections.emptyList()
    ),
    //교환철회
    EW(
            Arrays.asList(
                    OrderClaim.builder().ordClmTpCd("XC").dvRvtCcd("D").ordPrgsScd(OrdPrgsScdCode.EXCHANGE_WITHDRAWAL.code).build(),
                    OrderClaim.builder().ordClmTpCd("XC").dvRvtCcd("D").ordPrgsScd(OrdPrgsScdCode.EXCHANGE_WITHDRAWAL.code).build()
            ),
            Collections.emptyList()
    );

    private final List<OrderClaim> insertOrderClaim;
    private final List<OrderClaim> updateOrderClaim;
}
