package com.plateer.ec1.promotion.apply.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PromotionVO {

    //프로모션 번호
    private Long prmNo;
    //쿠폰 발급 번호
    private Long couponIssNo;
    //프로모션 이름
    private String prmNm;
    //정액,정률구분코드
    private String dcCode;
    //할인값
    private Long discountValue;
    //할인 적용 금액
    private Long discountedPrice;

    private String maxBenefitYn;


}
