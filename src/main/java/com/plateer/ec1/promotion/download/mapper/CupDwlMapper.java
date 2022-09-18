package com.plateer.ec1.promotion.download.mapper;

import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.com.vo.CupInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CupDwlMapper {

    CupInfoVO getCupDwlInfo(CupDwlRequestVO cupDwlRequestVO);

    CupInfoVO getOfflineCupInfo(CupDwlRequestVO cupDwlRequestVO);
}
