package com.qianyu.wxapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lijing
 * @date 2019-04-22-19:49
 * @discroption 日记实体类
 */
@Data
@ToString
@Component
@Entity
@Table(name = "diary")
public class Diary {
    // 主键
    @Id
    @GeneratedValue
    @Column(name = "did")
    private Integer id;
    // 用户主键
    @Column(name = "uid")
    private Integer uid;
    // 时间
    @Temporal(TemporalType.DATE)
    @Column(name = "dtime")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    // @JsonFormat(pattern="yyyy-MM-dd")
    private Date time;
    // 星期几
    @Column(name = "dweek")
    private String week;
    // 天气
    @Column(name = "dweather")
    private String weather;
    // 内容
    @Lob
    @Column(name = "dcontent",columnDefinition = "Text")
    private String content;
}
