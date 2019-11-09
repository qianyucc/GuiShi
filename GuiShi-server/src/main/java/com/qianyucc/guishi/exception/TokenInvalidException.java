package com.qianyucc.guishi.exception;

/**
 * @author lijing
 * @date 2019-10-12 11:34
 * @description 业务层token无效异常
 */
public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("非法token！");
    }

    public TokenInvalidException(String message) {
        super(message);
    }
}

