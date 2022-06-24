package com.plateer.ec1.promotion.factory.impl;

import com.plateer.ec1.common.model.product.Product;
import com.plateer.ec1.promotion.enums.PromotionType;
import com.plateer.ec1.promotion.factory.Calculation;
import com.plateer.ec1.promotion.vo.ApplicablePromotionVO;
import com.plateer.ec1.promotion.vo.PromotionVO;
import com.plateer.ec1.promotion.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.vo.response.PriceDiscountResponseVO;
import com.plateer.ec1.promotion.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j2
public class PriceDiscountCalculation implements Calculation {

    @Override
    public ResponseBaseVO getCalculationData(PrmRequestBaseVO prmRequestBaseVO) {
        log.info("가격조정 할인금액 계산");

        PriceDiscountResponseVO priceDiscountResponseVO = new PriceDiscountResponseVO();

        List<Product> priceDiscountAppliedProductList = new ArrayList<>();
        List<ApplicablePromotionVO> prmList = getApplicablePromotionList(prmRequestBaseVO);

        for (ApplicablePromotionVO applicablePromotionVO : prmList) {
            Product product = setUpMaxBnfDiscountPrice(applicablePromotionVO);
            priceDiscountAppliedProductList.add(product);
        }

        priceDiscountResponseVO.setMemberNo(prmRequestBaseVO.getMemberNo());
        priceDiscountResponseVO.setProductList(priceDiscountAppliedProductList);

        return priceDiscountResponseVO;
    }

    @Override
    public PromotionType getType() {
        return PromotionType.PRICE_DISCOUNT;
    }

    private Product setUpMaxBnfDiscountPrice(ApplicablePromotionVO applicablePromotionVO){
        //상품 1개당 적용 가능 가격조정 프로모션은 N개
        Product product = applicablePromotionVO.getProduct();
        List<PromotionVO> applicablePriceDiscountPrmList = applicablePromotionVO.getPromotionVOList();

        for (PromotionVO promotionVO : applicablePriceDiscountPrmList) {
            setDiscountedPrice(product.getProductPrice(), promotionVO);
        }

        //적용가능한 가격조정 금액 중 할인혜택이 가장 큰 가격조정 프로모션을 가져온다.
        PromotionVO maxBnfPromotion = getMaxBenefitPrm(applicablePriceDiscountPrmList);
        Long discountedPrice = maxBnfPromotion.getDiscountedPrice();

        //할인혜택이 가장 큰 가격조정 금액으로 Product의 가격조정금액을 셋팅한다.
        setProductDiscountedPrice(product, discountedPrice);

        return product;
    }

    private void setProductDiscountedPrice(Product product, Long discountedPrice){
        product.setDiscountedPrice(discountedPrice);
    }

    public List<ApplicablePromotionVO> getApplicablePromotionList(PrmRequestBaseVO prmRequestBaseVO) {
        //TODO prmDao.getApplicablePromotionList
        return new ArrayList<>();
    }

}
