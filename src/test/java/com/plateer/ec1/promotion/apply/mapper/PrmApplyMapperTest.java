package com.plateer.ec1.promotion.apply.mapper;

import com.plateer.ec1.common.model.product.Product;
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
        List<Product> productList = new ArrayList<>();
        Product product1 = Product.builder().goodsNo("P001").itemNo("1").orrAt(1000L).build();
        Product product2 = Product.builder().goodsNo("P001").itemNo("2").orrAt(1000L).build();
        Product product3 = Product.builder().goodsNo("P003").itemNo("2").orrAt(1000L).build();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        PrmRequestBaseVO prmRequestBaseVO = PrmRequestBaseVO.builder()
                .prmKindCd("20")
                .cpnKindCd("10")
                .mbrNo("user1")
                .productList(productList).build();

        List<PrmAplyVO> applicablePrmList = prmApplyMapper.getApplicablePrmList(prmRequestBaseVO);
        System.out.println(applicablePrmList);
    }
}