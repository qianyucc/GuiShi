package com.qianyucc.guishi.model.vo;

import com.qianyucc.guishi.model.enums.*;
import lombok.*;

import java.time.*;

/**
 * @author lijing
 * @date 2019-10-12 10:40
 * @description 封装返回信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespDataVO {
    private Long code;
    private String msg;
    private Object data;
    private Long timestamp;

    public static RespDataVO ok(String msg, Object data) {
        return new RespDataVO(ErrorCode.SUCCESS.getCode(), msg, data, Instant.now().toEpochMilli());
    }

    public static RespDataVO ok(String msg) {
        return new RespDataVO(ErrorCode.SUCCESS.getCode(), msg, null, Instant.now().toEpochMilli());
    }

    public static RespDataVO error(String msg, Object obj) {
        return new RespDataVO(ErrorCode.UNKNOW_ERROR.getCode(), msg, obj, Instant.now().toEpochMilli());
    }

    public static RespDataVO error(String msg) {
        return new RespDataVO(ErrorCode.UNKNOW_ERROR.getCode(), msg, null, Instant.now().toEpochMilli());
    }
}

