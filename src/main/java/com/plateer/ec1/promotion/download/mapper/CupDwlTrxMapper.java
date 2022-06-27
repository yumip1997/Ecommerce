package com.plateer.ec1.promotion.download.mapper;

import com.plateer.ec1.promotion.download.vo.request.CupDwlRequestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupDwlTrxMapper {

    void insertCup(CupDwlRequestVO cupDwlRequestVO);

    void updateCup(CupDwlRequestVO cupDwlRequestVO);

}
