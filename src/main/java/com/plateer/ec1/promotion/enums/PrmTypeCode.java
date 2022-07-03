package com.plateer.ec1.promotion.enums;

import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum PrmTypeCode {

    //가격조정
    PRICE_DISCOUNT("PD", PRM0001Code.PRICE_DISCOUNT, null),
    //상품쿠폰
    PROUDCT_COUPON("PC", PRM0001Code.COUPON, PRM0004Code.PRODUCT_COUPON),
    //장바구니쿠폰
    CART_COUPON("CC", PRM0001Code.COUPON, PRM0004Code.CART_COUPON);

    private final String code;
    private final PRM0001Code prmKindCd;
    private final PRM0004Code cpnKindCd;

    //TODO NULL 비교?
    public static PrmTypeCode findPromotionType(PrmRequestBaseVO prmRequestBaseVO){
        return Arrays.stream(PrmTypeCode.values())
                .filter(prmTypeCode -> prmTypeCode.getPrmKindCd().getCode().equals(prmRequestBaseVO.getPrmKindCd()))
                .filter(prmTypeCode -> prmRequestBaseVO.getCpnKindCd() == null || prmTypeCode.getCpnKindCd().getCode().equals(prmRequestBaseVO.getCpnKindCd()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(PromotionException.INVALID_PROMOTION_TYPE.getMSG()));
    }
}
