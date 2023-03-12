/*
package com.lyh.aspect;

import com.lyh.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * Author:crushlyh
 * Date:2023/3/4 21:44
 *//*

@Component
@Aspect
@Slf4j
public class LogAspect {
    //切点
    @Pointcut("@annotation(com.lyh.annotation.SystemLog)")
    public void pt(){

    }
    @Around("pt()")
    public Object pointlog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed;
        try {
            handleBefore(joinPoint);
            proceed = joinPoint.proceed();
            handleAfter();
        } finally {
            //结束换行
            log.info("========END======="+System.lineSeparator());
        }
        return proceed;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取被增强方法上的注解对象
        SystemLog systemLog=getSystemLog(joinPoint);
        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",request.get );
        // 打印 Http method
        log.info("HTTP Method    : {}", );
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", );
        // 打印请求的 IP
        log.info("IP             : {}",);
        // 打印请求入参
        log.info("Request Args   : {}",);
        // 打印出参
        log.info("Response       : {}", );
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
    }
}
*/
