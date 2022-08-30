package com.plateer.ec1.claim.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.plateer.ec1.order.enums.OPT0004Code.*;

@RequiredArgsConstructor
@Getter
public enum ValidOrdPrgs {

    BY_ORDER_COMPLETE(toSet(ORDER_WAITING.code, ORDER_COMPLETE.code)),
    IN_ORDER_COMPLETE(toSet(ORDER_COMPLETE.code)),
    IN_CANCEL_REQUEST(toSet(CANCEL_REQUEST.code)),
    IN_DELIVERY_COMPLETE(toSet(DELIVERY_COMPLETE.code)),
    IN_RETURN_ACCEPT(toSet(RETURN_ACCEPT.code)),
    IN_EXCHANGE_ACCEPT(toSet(EXCHANGE_ACCEPT.code));

    private final Set<String> validOrdPrgsList;

    public boolean isContains(String ordPrgs){
        return this.validOrdPrgsList.contains(ordPrgs);
    }

    private static Set<String> toSet(String... codes) {
        return new HashSet<>(Arrays.asList(codes));
    }


}
