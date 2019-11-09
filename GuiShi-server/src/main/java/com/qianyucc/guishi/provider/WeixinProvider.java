package com.qianyucc.guishi.provider;

import cn.hutool.core.map.*;
import cn.hutool.core.util.*;
import cn.hutool.http.*;
import com.alibaba.fastjson.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.sql.*;
import java.util.*;

/**
 * @author lijing
 * @date 2019-10-11 14:41
 * @description 用于封装第三方请求
 */
@Component
@Slf4j
public class WeixinProvider {
    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.secret}")
    private String secret;
    @Value("${weixin.grant-type}")
    private String grantType;
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type={}";

    public Map<String, Object> getSkeyId(String code) {
        String resp = HttpUtil.get(StrUtil.format(REQUEST_URL, appid, secret, code, grantType));
        JSONObject jo = JSON.parseObject(resp);
        HashMap<String, Object> map = new HashMap<>();
        map.put("sessionKey", jo.getString("session_key"));
        map.put("openid", jo.getString("openid"));
        return map;
    }
}