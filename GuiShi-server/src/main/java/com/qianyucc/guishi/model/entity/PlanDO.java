package com.qianyucc.guishi.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author lijing
 * @date 2019-04-22-19:37
 * @discroption 计划实体类
 */
@Data
@Table(name = "plan")
@Entity
public class PlanDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long uid;
    private String content;
    @Column(name = "start_time")
    private Long startTime;
    @Column(name = "end_time")
    private Long endTime;

    private Long createTime;
    private Integer status;
}
