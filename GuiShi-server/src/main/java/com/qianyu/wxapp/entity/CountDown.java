package com.qianyu.wxapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lijing
 * @date 2019-04-22-19:29
 * @discroption 倒计时实体类
 */
@Component
@Data
@ToString
@Entity
@Table(name = "count_down")
public class CountDown {
    // 主键
    @Id
    @GeneratedValue
    @Column(name = "cid")
    private Integer id;
    // 事件名称
    @Column(name = "cevent")
    private String event;
    // 结束时间
    @Temporal(TemporalType.DATE)
    @Column(name = "cendtime")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endTime;
    // 用户唯一标示
    @Column(name = "uid")
    private Integer uid;
    //在getter函数上面加上，@com.fasterxml.jackson.annotation.JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
}
