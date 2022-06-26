package com.plateer.ec1.promotion.download.mapper;

import com.plateer.ec1.promotion.download.vo.CupDwlVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CupDwlMapper {

    CupDwlVO getCupDwlInfo(long prmNo);

}
