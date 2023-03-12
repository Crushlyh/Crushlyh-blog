package com.lyh.handler.excetion;

import com.lyh.Exception.SystemException;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.enums.AppHttpCodeEnum;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author:crushlyh
 * Date:2023/2/24 21:47
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){

        //打印异常信息
        log.error("出现了异常！{}",e);
        //从异常对象中获取提示信息
        return ResponseResult.errorResult(e.getCode(),e.getMsg());

    }
    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler(Exception e){

        //打印异常信息
        log.error("出现了异常！{}",e);
        //从异常对象中获取提示信息
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());

    }
}
