package com.qianyucc.guishi.model.vo;

import cn.hutool.core.bean.*;
import cn.hutool.core.bean.copier.*;
import com.qianyucc.guishi.model.entity.*;
import lombok.*;

/**
 * @author lijing
 * @date 2019-11-01 16:00
 * @description
 */
@Data
public class UserVO {
    public static UserVO create(UserDO userDO) {
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userDO, userVO, CopyOptions.create().ignoreError());
        return userVO;
    }
    private Integer id;
    private String nickName;
    private Integer gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String token;

    private Long ucplans;//uncompleted plan count
}
