package com.plateer.ec1.promotion.com.mapper;

import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CupInfoMapper {

    Optional<CupInfoVO> getCupInfo(Long prmNo);

    List<CupInfoVO> getIssuedCupInfo(List<CupIssVO> cupIssVOList);

}
