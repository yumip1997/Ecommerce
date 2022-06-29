package com.plateer.ec1.promotion.com.mapper;

import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupInfoMapper {

    CupInfoVO getCupInfo(Long prmNo);

    CupInfoVO getIssuedCupInfo(Long cpnIssNo);

}
