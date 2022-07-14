package com.plateer.ec1.common.aop;

import com.plateer.ec1.common.vo.ValidResVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/*
ValidResVO의 응답 값 검증 Aspect
 */
@Component
@Aspect
public class ResValidAspect {

    @Before("@annotation(ResValid)")
    public void doValid(JoinPoint joinPoint){
        for(Object arg : joinPoint.getArgs()){
            isValid(arg);
        }
    }

    private void isValid(Object arg){
        if(arg instanceof ValidResVO){
            ValidResVO vo = (ValidResVO) arg;
            vo.isValidRes();
        }
    }
}
