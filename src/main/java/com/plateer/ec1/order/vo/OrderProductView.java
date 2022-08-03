package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpGoodsInfo;
import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductView {

    private OrderProductVO orderProductVO;
    private ProductInfoVO productInfoVO;

    public OpGoodsInfo toOpGoodsInfo(String ordNo){
        return OpGoodsInfo.builder()
                .ordNo(ordNo)
                .ordGoodsNo(orderProductVO.getGoodsNo())
                .ordItemNo(orderProductVO.getItemNo())
                .goodsSellTpCd(productInfoVO.getGoodsTpCd())
                .goodsDlvTpCd(productInfoVO.getGoodsDlvTpCd())
                .goodsNm(orderProductVO.getGoodsNm())
                .itemNm(orderProductVO.getItemNm())
                .sellAmt(orderProductVO.getSalePrc())
                .sellDcAmt(orderProductVO.getPrmPrc())
                .build();
    }
}
