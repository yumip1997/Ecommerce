package com.plateer.ec1.promotion.apply.controller;

import com.plateer.ec1.promotion.apply.service.PromotionService;
import com.plateer.ec1.promotion.apply.vo.request.PrmRequestBaseVO;
import com.plateer.ec1.promotion.apply.vo.response.ResponseBaseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @PostMapping("/promotion")
    public ResponseBaseVO getPromotion(@RequestBody PrmRequestBaseVO prmRequestBaseVO){
        return promotionService.getCalculationData(prmRequestBaseVO);
    }

}
