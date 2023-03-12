package com.lyh.handler.security;

import com.alibaba.fastjson.JSON;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author:crushlyh
 * Date:2023/2/24 21:25
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATER_AUTH);
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));

    }
}
