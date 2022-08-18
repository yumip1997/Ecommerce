package com.plateer.ec1.promotion.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PromotionException {

    INVALID_PROMOTION_TYPE("프로모션 유형이 올바르지 않습니다!"),
    INVALID_CUP_DWL_TYPE("다운로드 쿠폰 유형이 올바르지 않습니다!"),
    INVALID_DC_TYPE("할인 구분 유형이 올바르지 않습니다!"),
    NULL_CPN_CERT_NO("오프라인 쿠폰 다운로드시 인증코드는 필수입니다!"),
    NULL_CUP_MBR_NO("쿠폰 다운로드 시 회원 번호는 필수입니다!"),
    NULL_CUP_PRM_NO("쿠폰 다운로드시 시 프로모션 번호는 필수입니다!"),
    NOT_FIND_PRM("해당 프로모션을 조회할 수 없습니다!"),
    INVALID_CUP_DWL_CNT("다운로드 가능 수량을 초과하였습니다!"),
    INVALID_CUP_DWL_PERIOD("쿠폰 다운로드 가능 기간이 아닙니다!"),
    INVALID_CPN_CERT_NO("오프라인 쿠폰 인증코드가 올바르지 않습니다!"),
    INVALID_PRM_PERIOD("유효한 프로모션 기간이 아닙니다!"),
    ALREADY_USED_CUP("이미 사용한 쿠폰입니다!"),
    NOT_USED_CUP("복원 대상 쿠폰이 아닙니다!"),
    NOT_SAME_MBR_NO("쿠폰을 실제 발급받은 회원과 쿠폰사용취소 요청 회원이 일치하지 않습니다!"),
    INVALID_POINT_REMAIN("포인트 잔액이 부족합니다!");

    private final String MSG;
}
