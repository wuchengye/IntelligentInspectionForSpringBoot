package com.bda.bdaqm.shiro.realm;

import com.alibaba.fastjson.JSON;
import com.bda.bdaqm.RESTful.Result;
import com.bda.bdaqm.RESTful.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonAuthLoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        Subject subject = SecurityUtils.getSubject();

        if(null != subject){
            if(subject.isRemembered()){
                return Boolean.TRUE;
            }
            if(subject.isAuthenticated()){
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE ;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        out((HttpServletResponse) response, Result.failure(ResultCode.NO_PREMISSION));
        return Boolean.FALSE ;
    }

    public void out(HttpServletResponse response, Result jsonObj){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSON.toJSONString(jsonObj));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}