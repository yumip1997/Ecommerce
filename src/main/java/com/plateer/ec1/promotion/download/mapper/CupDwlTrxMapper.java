package com.plateer.ec1.promotion.download.mapper;

import com.plateer.ec1.promotion.apply.vo.request.CupDwlRequestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupDwlTrxMapper {

    void download(CupDwlRequestVO cupDwlRequestVO);

}
