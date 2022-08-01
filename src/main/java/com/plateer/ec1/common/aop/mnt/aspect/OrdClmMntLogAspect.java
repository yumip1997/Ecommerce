package com.plateer.ec1.common.aop.mnt.aspect;

import com.plateer.ec1.order.service.OrdClmMntService;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OrdClmMntLogAspect {

    private final OrdClmMntService ordClmMntService;

    @Pointcut("@annotation(com.plateer.ec1.common.aop.mnt.annotation.OrdClmMntLog)")
    private void mntLogAnnotation(){}

    @Pointcut("args(com.plateer.ec1.order.vo.base.OrderClaimBaseVO, ..)")
    private void orderClaimBaseArg(){}

    @Around("mntLogAnnotation() && orderClaimBaseArg() && args(arg,..)")
    public Object manipulateLog(ProceedingJoinPoint joinPoint, OrderClaimBaseVO arg) throws Throwable{
        Long logSeq = null;
        OrdClmCreationVO<?,?> result = null;

        try{
            logSeq = ordClmMntService.insertOrderHistory(arg);
            result = (OrdClmCreationVO<?,?>) joinPoint.proceed();
            return result;
        } finally {
            ordClmMntService.updateOrderHistory(logSeq, result);
        }

    }
}
