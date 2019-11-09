package com.qianyucc.guishi.model.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.stereotype.*;

import javax.persistence.*;

/**
 * @author lijing
 * @date 2019-04-20-18:28
 * @discroption 用户类
 */
@Data
@Entity
@Table(name = "user")
public class UserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "open_id")
    private String openId;
    @Column(name = "nick_name")
    private String nickName;
    private Integer gender;
    private String city;
    private String province;
    private String country;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "session_key")
    private String sessionKey;
    @Column(name = "token")
    private String token;
    @Column(name = "create_time")
    private Long createTime;
    @Column(name = "update_time")
    private Long updateTime;
}
