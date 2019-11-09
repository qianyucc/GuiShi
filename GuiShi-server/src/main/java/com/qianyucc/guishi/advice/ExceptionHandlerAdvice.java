package com.qianyucc.guishi.advice;

import com.qianyucc.guishi.exception.*;
import com.qianyucc.guishi.model.enums.*;
import com.qianyucc.guishi.model.vo.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import javax.servlet.http.*;
import java.time.*;

/**
 * @author lijing
 * @date 2019-10-12 10:37
 * @description 全局异常处理
 */
@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<RespDataVO> exception(Exception exception, HttpServletResponse response) {
        exception.printStackTrace();
        RespDataVO respDataVO = new RespDataVO();
        if (exception instanceof ApiException) {//api异常
            ApiException apiException = (ApiException) exception;
            if (apiException instanceof ApiTokenExpiredException) {
                response.setStatus(403);
            } else {
                response.setStatus(500);
            }
            respDataVO.setCode(apiException.getErrorCode());
        } else if (exception instanceof NoHandlerFoundException) {// 没有匹配的api
            respDataVO.setCode(ErrorCode.RESOURCES_NOT_FOUND.getCode());
            response.setStatus(404);
        } else {//未知异常
            respDataVO.setCode(ErrorCode.UNKNOW_ERROR.getCode());
            response.setStatus(500);
        }
        respDataVO.setTimestamp(Instant.now().toEpochMilli());
        respDataVO.setMsg(exception.getMessage());
        ResponseEntity<RespDataVO> responseEntity = new ResponseEntity<>(respDataVO, HttpStatus.valueOf(response.getStatus()));
        return responseEntity;
    }
}
