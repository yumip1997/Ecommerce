package com.plateer.ec1.promotion.download.mapper;

import com.plateer.ec1.promotion.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.vo.request.PrmRequestBaseVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupDwlTrxMapper {

    void download(CupDwlRequestVO cupDwlRequestVO);

}
