package com.qianyu.wxapp.repository;

import com.qianyu.wxapp.entity.CountDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author lijing
 * @date 2019-04-24-19:29
 * @discroption 倒计时的数据库访问层
 */
@Repository
public interface CountDownRepository extends JpaRepository<CountDown, Integer> {
    /**
     * 根据uid查询倒计时信息
     *
     * @param uid
     * @return
     */
    List<CountDown> findByUid(Integer uid);

    /**
     * 根据uid和endTime查找信息
     *
     * @param uid
     * @param endTime
     * @return
     */
    List<CountDown> findByUidAndEndTime(Integer uid, Date endTime);

    /**
     * 查询是否有重复信信息
     *
     * @param uid
     * @param endTime
     * @param event
     * @return
     */
    List<CountDown> findByUidAndEndTimeAndEvent(Integer uid, Date endTime, String event);

    /**
     * 查找日期在date后面的倒计时
     *
     * @param uid
     * @param date
     * @return
     */
    List<CountDown> findByUidAndEndTimeAfter(int uid, Date date);
}
