package com.qianyucc.guishi.model.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author lijing
 * @date 2019-04-22-19:49
 * @discroption 日记实体类
 */
@Data
@Entity
@Table(name = "diary")
public class DiaryDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long uid;
    @Column(name = "create_time")
    private Long createTime;
    @Column(name = "image_url")
    private String imageUrls;
    @Lob
    @Column(columnDefinition = "Text")
    private String content;
}
