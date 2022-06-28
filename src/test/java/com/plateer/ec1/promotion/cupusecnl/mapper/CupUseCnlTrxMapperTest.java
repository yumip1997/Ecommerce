package com.plateer.ec1.promotion.cupusecnl.mapper;

import com.plateer.ec1.common.model.promotion.CcCpnIssueModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CupUseCnlTrxMapperTest {

    @Autowired
    private CupUseCnlTrxMapper cupUseCnlTrxMapper;

    @Test
    @DisplayName("회원 번호가 실제 쿠폰 발급 회원과 일치 하지 않을 경우 쿠폰 복원 실패")
    void restore_fail_invalid_mbr(){
        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.builder().cpnIssNo(1L).mbrNo("user2").build();
        ccCpnIssueModel.setLoginId("FO");

        Long result = cupUseCnlTrxMapper.insertOrgCup(ccCpnIssueModel);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("발급되지 않은 쿠폰 복원 요청 시 쿠폰 복원 실패")
    void restore_fail_not_issued(){
        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.builder().cpnIssNo(1000L).mbrNo("user2").build();
        ccCpnIssueModel.setLoginId("FO");

        Long result = cupUseCnlTrxMapper.insertOrgCup(ccCpnIssueModel);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("쿠폰 복원 테스트 성공")
    void restore_pass(){
        CcCpnIssueModel ccCpnIssueModel = CcCpnIssueModel.builder().cpnIssNo(1L).mbrNo("user1").build();
        ccCpnIssueModel.setLoginId("FO");

        Long result = cupUseCnlTrxMapper.insertOrgCup(ccCpnIssueModel);

        assertThat(result).isNotNull();
    }
}