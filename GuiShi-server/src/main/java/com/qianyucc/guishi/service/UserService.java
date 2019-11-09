package com.qianyucc.guishi.service;

import cn.hutool.core.bean.*;
import cn.hutool.core.bean.copier.*;
import com.alibaba.fastjson.*;
import com.qianyucc.guishi.exception.*;
import com.qianyucc.guishi.model.dto.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.model.vo.*;
import com.qianyucc.guishi.provider.*;
import com.qianyucc.guishi.repository.*;
import com.qianyucc.guishi.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;

/**
 * @author lijing
 * @date 2019-11-01 15:29
 * @description
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeixinProvider weixinProvider;
    @Autowired
    private PlanRepository planRepository;

    public UserVO login(LoginDTO loginDTO) {
        Map<String, Object> map = weixinProvider.getSkeyId(loginDTO.getCode());
        UserDO userInfo = JSON.parseObject(loginDTO.getRawData(), UserDO.class);
        userInfo.setSessionKey((String) map.get("sessionKey"));
        userInfo.setOpenId((String) map.get("openid"));
        String token = UUID.randomUUID().toString();
        userInfo.setToken(token);

        UserDO userExample = new UserDO();
        userExample.setOpenId(userInfo.getOpenId());
        Optional<UserDO> one = userRepository.findOne(Example.of(userExample));
        if (one.isPresent()) {
            UserDO userDO = one.get();
            BeanUtil.copyProperties(userInfo, userDO, CopyOptions.create().ignoreError().ignoreNullValue());
            userDO.setUpdateTime(Instant.now().toEpochMilli());
            UserVO userVO = UserVO.create(userRepository.save(userDO));
            userVO.setUcplans(planRepository.countByUidAndStatusAndCreateTimeAfter(userDO.getId(), 0, GlobalUtil.getCurrentDateMillions()));
            return userVO;
        } else {
            userInfo.setCreateTime(Instant.now().toEpochMilli());
            userInfo.setUpdateTime(userInfo.getCreateTime());
            return UserVO.create(userRepository.save(userInfo));
        }
    }

    public UserVO find(Long id) {
        return userRepository.findById(id)
                .map(userDO -> {
                    UserVO userVO = UserVO.create(userDO);
                    userVO.setUcplans(planRepository.countByUidAndStatusAndCreateTimeAfter(userDO.getId(), 0, GlobalUtil.getCurrentDateMillions()));
                    return userVO;
                })
                .orElseThrow(() -> new UserNotFoundException());
    }
}
