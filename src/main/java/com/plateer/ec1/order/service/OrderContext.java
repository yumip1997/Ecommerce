package com.plateer.ec1.order.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.common.aop.mnt.annotation.OrdClmMntLog;
import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.factory.AfterStrategyFactory;
import com.plateer.ec1.order.factory.DataStrategyFactory;
import com.plateer.ec1.order.mapper.OrdTrxMapper;
import com.plateer.ec1.order.strategy.after.AfterStrategy;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.validator.OrderValidator;
import com.plateer.ec1.order.vo.*;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.product.service.ProductInfoService;
import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderContext {
    private final DataStrategyFactory dataStrategyFactory;
    private final AfterStrategyFactory afterStrategyFactory;

    private final PayService payService;
    private final ProductInfoService productInfoService;
    private final OrdTrxMapper ordTrxMapper;

    @LogTrace @OrdClmMntLog
    @Transactional
    public OrdClmCreationVO<OrderVO, Object> doOrderProcess(OrderRequestVO orderRequestVO){
        OrderProductViewVO ordProductViewVO = getOrdProductViewVO(orderRequestVO);
        validate(orderRequestVO, ordProductViewVO);
        //주문데이터생성
        OrderVO orderVO = create(orderRequestVO, ordProductViewVO);
        //주문데이터등록
        //결제호출
        payService.approve(orderVO.toPayApproveReqVO());
        return orderVO.toOrdClmCreationVO();
    }

    public void doOrderAfterProcess(OrderRequestVO orderRequestVO, OrderVO orderVO) {
        AfterStrategy afterStrategy = getAfterStrategy(orderRequestVO.getSystemType());
        afterStrategy.call(orderRequestVO, orderVO);
    }

    private OrderProductViewVO getOrdProductViewVO(OrderRequestVO orderRequestVO){
        List<ProductInfoVO> param = convertProductInfoVOList(orderRequestVO.getOrderProductVOList());

        List<ProductInfoVO> productInfoVOList = productInfoService.getProductInfoVo(param);
        Map<String, ProductInfoVO> productInfoVOMap = productInfoVOList.stream()
                .collect(Collectors.toMap(ProductInfoVO::getGoodNoItemNo, Function.identity()));

        return OrderProductViewVO.builder()
                .orderRequestVO(orderRequestVO)
                .productInfoVOList(productInfoVOList)
                .productInfoVOMap(productInfoVOMap)
                .build();
    }

    private List<ProductInfoVO> convertProductInfoVOList(List<OrderProductVO> productVOList){
        return productVOList.stream()
                .map(OrderProductBaseVO::toProductInfoVO)
                .collect(Collectors.toList());
    }

    private void validate(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO){
        OrderValidator orderValidator = OrderValidator.findOrderValidator(orderRequestVO);
        boolean isValid = orderValidator.test(orderProductViewVO);

        if(isValid) return;
        throw new BusinessException(OrderException.INVALID_ORDER.msg);
    }

    private OrderVO create(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO){
        DataStrategy dataStrategy = getDataStrategy(orderRequestVO.getOrderType());
        return dataStrategy.create(orderRequestVO, orderProductViewVO);
    }

    private DataStrategy getDataStrategy(String typeStr){
        OPT0001Code OPT0001Code = com.plateer.ec1.order.enums.OPT0001Code.findOrderType(typeStr);
        return dataStrategyFactory.get(OPT0001Code);
    }

    private AfterStrategy getAfterStrategy(String typeStr){
        SystemType systemType = SystemType.findSystemType(typeStr);
        return afterStrategyFactory.get(systemType);
    }
}
