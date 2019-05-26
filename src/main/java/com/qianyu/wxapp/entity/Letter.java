package com.qianyu.wxapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lijing
 * @date 2019-04-22-19:44
 * @discroption 信件实体类
 */
@Entity
@Data
@ToString
@Component
@Table(name = "letter")
public class Letter {
    // 主键
    @Id
    @GeneratedValue
    @Column(name = "lid")
    private Integer id;
    // 用户主键
    @Column(name = "uid")
    private Integer uid;
    // 标题
    @Column(name = "ltitle")
    private String title;
    // 内容
    @Lob
    @Column(name = "lcontent",columnDefinition = "Text")
    private String content;
    // 用于通知的邮箱
    @Column(name = "lmail")
    private String mail;
    // 通知的时间
    @Temporal(TemporalType.DATE)
    @Column(name = "lnotice_time")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date noticeTime;
    // 通知的时间
    @Column(name = "lcreate_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
}
