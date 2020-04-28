package com.bda.bdaqm.shiro.realm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyMvcExceptionHandler implements HandlerExceptionResolver {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        logger.error(ex.getMessage(), ex);

        if (ex instanceof UnauthenticatedException || ex instanceof AuthenticationException || ex instanceof UnknownSessionException) {
            return outJson(request, response, Result.failure(ResultCode.NOT_LOGIN));
        }
        if (ex instanceof UnauthorizedException || ex instanceof AuthorizationException) {
            return outJson(request, response,  Result.failure(ResultCode.NOT_LOGIN));
        }
        return outJson(request, response,  Result.failure(ResultCode.NOT_LOGIN));
    }

    private ModelAndView outJson(HttpServletRequest request, HttpServletResponse response, Result res) {
        ModelAndView mv = new ModelAndView();
        /*  使用response返回    */
        response.setStatus(HttpStatus.OK.value()); //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        response.setCharacterEncoding("UTF-8"); //避免乱码
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            String json = JSON.toJSONString(res);
            response.getWriter().write(json);
        } catch (IOException e) {
            logger.error("与客户端通讯异常:" + e.getMessage(), e);
        }

        return mv;
    }
}