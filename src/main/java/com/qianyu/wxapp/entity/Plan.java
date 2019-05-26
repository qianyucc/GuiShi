package com.qianyu.wxapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lijing
 * @date 2019-04-22-19:37
 * @discroption 计划实体类
 */
@Data
@ToString
@Component
@Table(name = "plan")
@Entity
public class Plan {
    // 主键
    @Id
    @GeneratedValue
    @Column(name = "pid")
    private Integer id;
    // 用户主键
    @Column(name = "uid")
    private Integer uid;
    // 计划内容
    @Column(name = "pcontent")
    private String content;
    // 计划开始时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pstarttime")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private Date startTime;
    // 计划结束时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pendtime")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private Date endTime;
    // 完成状态
    @Column(name = "pstatus")
    private Integer status;
}
