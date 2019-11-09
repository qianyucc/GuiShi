package com.qianyucc.guishi.model.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.stereotype.*;

import javax.persistence.*;
import java.util.*;

/**
 * @author lijing
 * @date 2019-04-22-19:44
 * @discroption 信件实体类
 */
@Entity
@Data
@Table(name = "letter")
public class LetterDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long uid;
    private String title;
    @Lob
    @Column(columnDefinition = "Text")
    private String content;
    private String mail;
    @Column(name = "notice_time")
    private Long noticeTime;
    @Column(name = "create_time")
    private Long createTime;
}
