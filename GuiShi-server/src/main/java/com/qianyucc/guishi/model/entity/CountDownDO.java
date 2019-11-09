package com.qianyucc.guishi.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author lijing
 * @date 2019-04-22-19:29
 * @discroption 倒计时实体类
 */
@Data
@Entity
@Table(name = "count_down")
public class CountDownDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long uid;
    private String event;
    @Column(name = "end_time")
    private Long endTime;
    private Long createTime;
    private Integer status;
}
