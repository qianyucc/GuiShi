package com.qianyucc.guishi.model.enums;

/**
 * @author lijing
 * @date 2019-10-12 10:30
 * @description 全局错误码
 */
public enum ErrorCode {
    TOKEN_EXPIRED(10001L),
    UNKNOW_ERROR(50000L),
    SUCCESS(20000L),
    RESOURCES_NOT_FOUND(40004L),
    USER_NOT_FOUND(10002L);
    private Long code;

    ErrorCode(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
