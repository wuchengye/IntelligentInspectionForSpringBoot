package com.bda.bdaqm.admin.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bda.bdaqm.admin.model.LogRecord;
import com.bda.bdaqm.admin.model.User;
import com.bda.bdaqm.admin.service.LogRecordService;
import com.bda.bdaqm.util.DateUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private int count = 0;
	
	@Autowired
	private LogRecordService logService;
	
    /**  
     * 在业务处理器处理请求之前被调用  
     * 如果返回false  
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     * 如果返回true  
     *    执行下一个拦截器,直到所有的拦截器都执行完毕  
     *    再执行被拦截的Controller  
     *    然后进入拦截器链,  
     *    从最后一个拦截器往回执行所有的postHandle()  
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()  
     */    
    @Override    
    public boolean preHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler) throws Exception {    
        
        log.info("==============执行顺序: 1、preHandle================");    
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        
        log.info("requestUri:"+requestUri);    
        log.info("contextPath:"+contextPath);    
        log.info("url:"+url);   
        
        if("/admin".equals(url)){
        	this.count ++;
        }else if("/admin/login".equals(url)){
        	this.count = 0;
        }
          
        return true;     
    }    
    
    /** 
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作    
     * 可在modelAndView中加入数据，比如当前时间 
     */  
    @Override    
    public void postHandle(HttpServletRequest request,    
				            HttpServletResponse response, Object handler,    
				            ModelAndView modelAndView) throws Exception { 
    	
    	Subject subject = SecurityUtils.getSubject();
		User currentUser = (User) subject.getPrincipal(); //当前用户
    	
        log.info("==============执行顺序: 2、postHandle================"); 
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());
        
        log.info("requestUri:"+requestUri);    
        log.info("contextPath:"+contextPath);    
        log.info("url:"+url);
        
		if(this.count == 1){
			log.info("==============添加登陆记录================"); 
			//LogRecord logRecord = new LogRecord(currentUser.getUserId(), DateUtils.getChinaTime(), null);
			//logService.add(logRecord);
		}
		
    }    
    
    /**  
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等   
     *   
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()  
     */    
    @Override    
    public void afterCompletion(HttpServletRequest request,    
            HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {    
        //log.info("==============执行顺序: 3、afterCompletion================");    
    }    
	
}
