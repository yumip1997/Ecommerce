package com.plateer.ec1.order.controller;

import com.plateer.ec1.order.service.OrderService;
import com.plateer.ec1.order.vo.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public void order(@RequestBody OrderRequest orderRequest){
        try{
            orderService.order(orderRequest);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

}
