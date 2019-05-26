package com.qianyu.wxapp.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lijing
 * @date 2019-04-20-18:20
 * @discroption 微信小程序业务层接口
 */
public interface WxService {
    /**
     * 实现用户登陆操作，以及获取session_key
     *
     * @param code
     * @param rawData
     * @param signature
     * @param encryptedData
     * @param iv
     * @return
     */
    Map<String, Object> login(String code, String rawData, String signature, String encryptedData, String iv);

    /**
     * 向数据库中添加倒计时信息
     *
     * @param realData
     * @return
     */
    HashMap<String, Object> addCountDown(JSONObject realData);

    /**
     * 向数据库中中添加信件
     *
     * @param realData
     * @return
     */
    HashMap<String, Object> addLetter(JSONObject realData);

    /**
     * 添加计划
     *
     * @param realData
     * @return
     */
    HashMap<String, Object> addPlan(JSONObject realData);

    /**
     * 向数据库中添加日记
     *
     * @param realData
     * @return
     */
    HashMap<String, Object> addDiary(JSONObject realData);

    /**
     * 获取所有倒计时
     *
     * @param uid
     * @return
     */
    Map<String, Object> findAllCountDowns(int uid);

    /**
     * 根据日期获取倒计时
     *
     * @param uid
     * @param date
     * @return
     */
    Map<String, Object> findCountDownByDate(int uid, Date date);

    /**
     * 获取今天的计划
     *
     * @return
     */
    Map<String, Object> getPlanToday(int uid);

    /**
     * 获取所有计划
     *
     * @return
     */
    Map<String, Object> getAllPlan(int uid);

    /**
     * 根据日期获取信件
     *
     * @param uid
     * @param parse
     * @return
     */
    Map<String, Object> getLetter(int uid, Date parse);

    /**
     * 获取所有信件
     *
     * @param uid
     * @return
     */
    Map<String, Object> getAllLetter(int uid);

    /**
     * 查询指定日期的信件
     *
     * @param uid
     * @param parse
     * @return
     */
    Map<String, Object> getDiary(int uid, Date parse);

    /**
     * 获取全部信件
     *
     * @param uid
     * @return
     */
    Map<String, Object> getAllDiary(int uid);

    /**
     * 删除指定倒计时
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteCountDown(int id);

    /**
     * 删除指定信件
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteLetter(int id);

    /**
     * 删除指定日记
     *
     * @param id
     * @return
     */
    Map<String, Object> deleteDiary(int id);

    /**
     * 删除指定计划
     *
     * @param id
     * @return
     */
    Map<String, Object> deletePlan(int id);

    /**
     * 更新倒计时信息
     *
     * @param id
     * @param data
     * @return
     */
    Map<String, Object> upadteCountDown(int id, JSONObject data);

    /**
     * 更新信件信息
     *
     * @param id
     * @param data
     * @return
     */
    Map<String, Object> upadteLetter(int id, JSONObject data);

    /**
     * 更新日记信息
     *
     * @param id
     * @param data
     * @return
     */
    Map<String, Object> upadteDiary(int id, JSONObject data);

    /**
     * 更新计划信息
     *
     * @param id
     * @param data
     * @return
     */
    Map<String, Object> upadtePlan(int id, JSONObject data);

    /**
     * 获取计划完成和未完成数
     *
     * @param uid
     * @return
     */
    Map<String, Integer> getPlanCount(Integer uid);

    /**
     * 按照日期分类排序计划
     *
     * @param uid
     * @return
     */
    Map<String, List<Map<String, Object>>> getPlanGroupByDate(Integer uid);

    /**
     * 查找未完成的倒计时
     *
     * @param uid
     * @return
     */
    Map<String, Object> findCountDownAfterNow(int uid);
}
