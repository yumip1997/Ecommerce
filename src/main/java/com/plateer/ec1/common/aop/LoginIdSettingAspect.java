package com.plateer.ec1.common.aop;

import com.plateer.ec1.common.model.BaseModel;
import com.plateer.ec1.common.vo.BaseVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class LoginIdSettingAspect {

    @Before("execution(* *..*TrxMapper.*(..))")
    public void setLogindId(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
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
        if(arg instanceof BaseVO){
            setBaseVO(arg);
        }

        if(arg instanceof BaseModel){
            setBaseModel(arg);
        }
    }

    private void setBaseVO(Object arg){
        BaseVO baseVO = (BaseVO) arg;
        baseVO.setLoginId("FO");
    }

    private void setBaseModel(Object arg){
        BaseModel baseModel = (BaseModel) arg;
        baseModel.setLoginId("FO");
    }

}
