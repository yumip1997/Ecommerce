package com.plateer.ec1.common.aop.log.aspect;

import com.plateer.ec1.common.aop.log.TraceLogger;
import com.plateer.ec1.common.aop.log.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class LogTraceAspect {

    private final TraceLogger traceLogger;

    @Pointcut("execution(* com.plateer.ec1.payment..*.*(..))")
    private void allPayment(){}

    @Pointcut("@annotation(com.plateer.ec1.common.aop.log.annotation.LogTrace)")
    private void LogTraceAnnotation(){}

    @Around("allPayment() || LogTraceAnnotation()")
    public Object traceLog(ProceedingJoinPoint joinPoint) throws Throwable{
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = traceLogger.begin(message);

            Object result = joinPoint.proceed();

            traceLogger.end(status);
            return result;
        } catch (Exception e) {
            traceLogger.occurException(status, e);
            throw e;
        }
    }
}
