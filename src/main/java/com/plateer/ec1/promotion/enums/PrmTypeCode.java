package com.plateer.ec1.promotion.enums;

import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    public static PrmTypeCode findPromotionType(PrmRequestBaseVO prmRequestBaseVO){
        String prmKindCd = prmRequestBaseVO.getPrmKindCd();
        String cpnKindCd = prmRequestBaseVO.getCpnKindCd();

        if(PRICE_DISCOUNT.prmKindCd.getCode().equals(prmKindCd)){
            return PRICE_DISCOUNT;
        }

        if(PROUDCT_COUPON.prmKindCd.getCode().equals(prmKindCd) && PROUDCT_COUPON.cpnKindCd.getCode().equals(cpnKindCd)){
            return PROUDCT_COUPON;
        }

        if(CART_COUPON.prmKindCd.getCode().equals(prmKindCd) && CART_COUPON.cpnKindCd.getCode().equals(cpnKindCd)){
            return CART_COUPON;
        }

        throw new IllegalArgumentException(PromotionException.INVALID_PROMOTION_TYPE.getMSG());
    }
}
