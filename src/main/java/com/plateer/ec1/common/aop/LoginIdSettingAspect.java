package com.plateer.ec1.common.aop;

import com.plateer.ec1.common.vo.BaseVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@Aspect
public class LoginIdSettingAspect {

    @Before("@annotation(LoginIdSetting)")
    public void setLogindId(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            //TODO 수정 필요
            if(arg instanceof List){
                for (Object o : (List) arg) {
                    setLoginIdOfArgs(o);
                }
                continue;
            }

            setLoginIdOfArgs(arg);
        }
    }

    private void setLoginIdOfArgs(Object arg){
        if(! (arg instanceof BaseVO) ) return;

        BaseVO baseVO = (BaseVO) arg;
        baseVO.setLoginId("FO");
    }

}
