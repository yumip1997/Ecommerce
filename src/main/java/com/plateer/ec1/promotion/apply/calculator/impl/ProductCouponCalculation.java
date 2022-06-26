package com.plateer.ec1.promotion.apply.calculator.impl;

import com.plateer.ec1.common.model.product.Product;
import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.apply.calculator.Calculation;
import com.plateer.ec1.promotion.apply.vo.ApplicablePromotionVO;
import com.plateer.ec1.promotion.apply.vo.ProductCouponVO;
import com.plateer.ec1.promotion.apply.vo.PromotionVO;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ProductCouponResponseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j2
public class ProductCouponCalculation implements Calculation {

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        log.info("상품쿠폰 할인금액 계산");

        ProductCouponResponseVO productCouponResponseVO = new ProductCouponResponseVO();
        
        List<ApplicablePromotionVO> applicablePrmList = getApplicablePromotionList(prmRequestBaseVO);
        List<ProductCouponVO> productCouponVOList = new ArrayList<>();

        for (ApplicablePromotionVO applicablePromotionVO : applicablePrmList) {
            //상품 1개 , 적용가능한 상품쿠폰 N개
            Product product = applicablePromotionVO.getProduct();
            Long productPrice = product.getProductPrice();
            List<PromotionVO> promotionVOList = applicablePromotionVO.getPromotionVOList();

            //쿠폰적용금액 셋팅
            setDiscountedPriceOfPrdCupList(productPrice, promotionVOList);

            if(!CollectionUtils.isEmpty(promotionVOList)){
                PromotionVO maxBnfPrm = getMaxBenefitPrm(promotionVOList);
                maxBnfPrm.setMaxBenefitYn("Y");
            }

            ProductCouponVO productCouponVO = setUpProductCouponVO(product, promotionVOList);
            productCouponVOList.add(productCouponVO);
        }

        productCouponResponseVO.setMemberNo(prmRequestBaseVO.getMemberNo());
        productCouponResponseVO.setProductCouponVOList(productCouponVOList);

        return productCouponResponseVO;
    }

    @Override
    public PromotionType getType() {
        return PromotionType.PROUDCT_COUPON;
    }


    public List<ApplicablePromotionVO> getApplicablePromotionList(PrmRequestBaseVO prmRequestBaseVO) {
        //TODO prmDao.getApplicablePromotionList
        return new ArrayList<>();
    }

    private void setDiscountedPriceOfPrdCupList(Long productPrice, List<PromotionVO> promotionVOList){
        for(PromotionVO promotionVO : promotionVOList){
            setDiscountedPrice(productPrice, promotionVO);
        }
    }

    private ProductCouponVO setUpProductCouponVO(Product product, List<PromotionVO> promotionVOList){
        return ProductCouponVO.builder().product(product).promotionVOList(promotionVOList).build();
    }
    
}
