package com.plateer.ec1.promotion.com.mapper;

import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CupInfoMapper {

    Optional<CupInfoVO> getCupInfo(Long prmNo);

    Optional<CupInfoVO> getIssuedCupInfo(Long cpnIssNo);

}
