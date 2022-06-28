package com.plateer.ec1.promotion.cupusecnl.mapper;

import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupRestoreRequestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupUseCnlMapper {

    CupInfoVO getOrgCupInfo(CupRestoreRequestVO cupRestoreRequestVO);

}
