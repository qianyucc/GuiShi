package com.qianyucc.guishi.model.dto;

import lombok.*;

/**
 * @author lijing
 * @date 2019-10-31 14:23
 * @description
 */
@Data
public class LetterDTO {
    private Long uid;
    private String title;
    private String content;
    private String mail;
    private String noticeTime;
}
