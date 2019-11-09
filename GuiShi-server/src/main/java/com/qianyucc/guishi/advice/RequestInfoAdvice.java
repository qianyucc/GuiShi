package com.qianyucc.guishi.advice;

import lombok.extern.slf4j.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.context.request.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * @author lijing
 * @date 2019-08-18 18:12
 * @description 配置aop打印请求信息
 */
@Component
@Aspect
@Slf4j
public class RequestInfoAdvice {

    @Pointcut("execution(public * com.qianyucc.guishi.controller.*.*(..))")
    public void controllerLog() {
    }

    @Before("controllerLog()")
    public void printRequestInfo() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        log.info("request-url:{}", request.getRequestURL());
        log.info("request-method:{}", request.getMethod());
        log.info("remote-address:{}", request.getRemoteAddr());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            log.info("name:{},value:{}", name, request.getParameter(name));
        }

    }
}
