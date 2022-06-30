package com.plateer.ec1.promotion.apply.mapper;

import com.plateer.ec1.product.vo.ProductInfoVO;
import com.plateer.ec1.promotion.apply.vo.PrmAplyVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PrmApplyMapperTest {

    @Autowired
    private PrmApplyMapper prmApplyMapper;

    @Test
    void test(){
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        ProductInfoVO productInfoVO1 = ProductInfoVO.builder().goodsNo("P001").itemNo("1").orrAt(1000L).build();
        ProductInfoVO productInfoVO2 = ProductInfoVO.builder().goodsNo("P001").itemNo("2").orrAt(1000L).build();
        ProductInfoVO productInfoVO3 = ProductInfoVO.builder().goodsNo("P003").itemNo("2").orrAt(1000L).build();
        productInfoVOList.add(productInfoVO1);
        productInfoVOList.add(productInfoVO2);
        productInfoVOList.add(productInfoVO3);
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder()
                .prmKindCd("20")
                .cpnKindCd("10")
                .mbrNo("user1")
                .productInfoVOList(productInfoVOList).build();

        List<PrmAplyVO> applicablePrmList = prmApplyMapper.getApplicablePrmList(prmRequestBaseVO);
        System.out.println(applicablePrmList);
    }
}