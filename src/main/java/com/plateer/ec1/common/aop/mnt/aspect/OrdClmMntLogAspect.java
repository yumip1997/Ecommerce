package com.plateer.ec1.common.aop.mnt.aspect;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.order.enums.OPT0012Code;
import com.plateer.ec1.order.service.OrdClmMntService;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
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
        OrdClmCreationVO<?,?> result = null;
        Long logSeq = null;
        String procCcd = OPT0012Code.SC.getCode();

        try{
            logSeq = ordClmMntService.insertOrderHistory(arg);
            result = (OrdClmCreationVO<?,?>) joinPoint.proceed();
            return result;
        }catch (Exception exception){
            //Exception 유형에 따라 procCcd 넣어주기
            //유효성 검증 실패 Exception
            //데이터 생성 실패 Exception
            //결제 실패 Exception
            throw exception;
        }finally {
            ordClmMntService.updateOrderHistory(logSeq, result, procCcd);
        }
    }
}
