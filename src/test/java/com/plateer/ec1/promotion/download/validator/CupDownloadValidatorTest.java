package com.plateer.ec1.promotion.download.validator;

import com.plateer.ec1.promotion.apply.vo.request.CupDwlRequestVO;
import com.plateer.ec1.promotion.download.mapper.CupDwlMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CupDownloadValidatorTest {

    @Autowired
    private CupDownloadValidator cupDownloadValidator;

    @Test
    void test(){
        CupDwlRequestVO cupDwlRequestVO = CupDwlRequestVO.builder().prmNo(1).mbrNo("33").build();
        cupDownloadValidator.isValid(cupDwlRequestVO);
    }
}