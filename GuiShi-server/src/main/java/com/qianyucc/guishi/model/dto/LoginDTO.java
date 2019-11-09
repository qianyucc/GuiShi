package com.qianyucc.guishi.model.dto;

import lombok.*;

/**
 * @author lijing
 * @date 2019-11-01 14:33
 * @description
 */
@Data
public class LoginDTO {
    private String code;
    private String rawData;
    private String signature;
    private String encryptedData;
    private String iv;
}
