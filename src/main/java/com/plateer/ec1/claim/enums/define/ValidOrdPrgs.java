package com.plateer.ec1.claim.enums.define;

import com.plateer.ec1.order.enums.OPT0004Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ValidOrdPrgs {

    BY_ORDER_COMPLETE(Arrays.asList(OPT0004Code.ORDER_WAITING.code, OPT0004Code.ORDER_COMPLETE.code)),
    IN_ORDER_COMPLETE(Collections.singletonList(OPT0004Code.ORDER_COMPLETE.code)),
    IN_CANCEL_REQUEST(Collections.singletonList(OPT0004Code.CANCEL_REQUEST.code)),
    IN_DELIVERY_COMPLETE(Collections.singletonList(OPT0004Code.DELIVERY_COMPLETE.code)),
    IN_RETURN_ACCEPT(Collections.singletonList(OPT0004Code.RETURN_ACCEPT.code)),
    IN_EXCHANGE_ACCEPT(Collections.singletonList(OPT0004Code.EXCHANGE_ACCEPT.code));

    private final List<String> validOrdPrgsList;

    public boolean isContains(String ordPrgs){
        return this.validOrdPrgsList.contains(ordPrgs);
    }


}
