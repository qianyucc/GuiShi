package com.qianyucc.guishi.exception;

import com.qianyucc.guishi.model.enums.*;

/**
 * @author lijing
 * @date 2019-10-12 10:28
 * @description token过期异常
 */
public class ApiTokenExpiredException extends ApiException {
    public ApiTokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED.getCode(),"token已过期");
    }
}
