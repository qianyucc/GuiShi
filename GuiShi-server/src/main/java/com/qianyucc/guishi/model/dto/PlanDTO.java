package com.qianyucc.guishi.model.dto;

import lombok.*;

/**
 * @author lijing
 * @date 2019-10-30 22:50
 * @description
 */
@Data
public class PlanDTO {
    private Long id;
    private Long uid;
    private String content;
    private String startTime;
    private String endTime;
}
