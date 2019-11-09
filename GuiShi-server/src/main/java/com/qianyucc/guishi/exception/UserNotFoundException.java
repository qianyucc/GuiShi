package com.qianyucc.guishi.exception;

import com.qianyucc.guishi.model.enums.*;

/**
 * @author lijing
 * @date 2019-11-02 20:43
 * @description
 */
public class UserNotFoundException extends ApiException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND.getCode(), "找不到当前用户");
    }
}
