package com.qianyu.wxapp.repository;

import com.qianyu.wxapp.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author lijing
 * @date 2019-04-24-19:31
 * @discroption 的数据库访问层
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
    /**
     * 根据uid查找plan集合
     *
     * @param uid
     * @return
     */
    List<Plan> findByUid(int uid);

    /**
     * 根据uid和startDate查询集合
     *
     * @param startDate
     * @return
     */
    List<Plan> findByUidAndStartTimeAfter(int uid, Date startDate);

    /**
     * 查找是否有重复项
     *
     * @param uid
     * @param endTime
     * @param startTime
     * @param content
     * @return
     */
    List<Plan> findByUidAndEndTimeAndStartTimeAndContent(Integer uid, Date endTime, Date startTime, String content);

    /**
     * 查询指定状态的计划数
     * @param status
     * @return
     */
    Integer countAllByStatusAndUid(Integer status,Integer uid);
}
