package com.plateer.ec1.promotion.download.mapper;

import com.plateer.ec1.promotion.apply.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.download.vo.CupDwlVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CupDwlMapperTest {

    @Autowired
    private CupDwlMapper cupDwlMapper;

    @Test
    void test(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().prmNo(1).mbrNo("33").build();
        CupDwlVO cupDwlVO = cupDwlMapper.getCupDwlInfo(cupDwlRequestVO);
        System.out.println(cupDwlVO.toString());
    }
}