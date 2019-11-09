package com.qianyu.wxapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * @author lijing
 * @date 2019-04-20-18:28
 * @discroption 用户类
 */
@Component
@Data
@ToString
@Entity
@Table(name = "user")
public class User {
    //用户主键
    @Id
    @GeneratedValue
    @Column(name = "uid")
    private Integer id;
    //用户的openid
    @Column(name = "uopenid")
    private String openId;
    //用户的微信昵称
    @Column(name = "uname")
    private String name;
    //用户性别
    @Column(name = "ugender")
    private Integer gender;
    //用户地址
    @Column(name = "uaddress")
    private String address;
    //用户头像封面地址
    @Column(name = "uavatar")
    private String avatar;
    //真正的登录态标识
    @Column(name = "sessionkey")
    private String sessionKey;
    //自定义登录态标识
    @Column(name = "skey")
    private String skey;
    //用户首次登录时间
    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private java.util.Date createTime;
    //用户最近登录时间
    @Column(name = "update_time")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private java.util.Date updateTime;
}
