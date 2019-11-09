package com.qianyu.wxapp.repository;

import com.qianyu.wxapp.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author lijing
 * @date 2019-04-24-19:35
 * @discroption 日记的数据库访问层
 */
@Repository
public interface DiaryRepository extends JpaRepository<Diary, Integer> {
    /**
     * 根据uid查询日记列表
     *
     * @param uid
     * @return
     */
    List<Diary> findByUid(int uid);

    /**
     * 根据uid和time查询日记列表
     *
     * @param uid
     * @param time
     * @return
     */
    List<Diary> findByUidAndTime(int uid, Date time);

    /**
     * 查找重复数据
     *
     * @param uid
     * @param time
     * @param weather
     * @param week
     * @param content
     * @return
     */
    List<Diary> findByUidAndTimeAndWeatherAndWeekAndContent(Integer uid, Date time, String weather, String week, String content);
}
