package com.qianyucc.guishi.repository;

import com.qianyucc.guishi.model.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

/**
 * @author lijing
 * @date 2019-04-24-19:31
 * @discroption 的数据库访问层
 */
public interface PlanRepository extends JpaRepository<PlanDO, Long> {
    List<PlanDO> findByUid(Long uid);
    List<PlanDO> findByUidAndCreateTimeAfter(Long uid, Long createTime);
    Long countByUidAndStatusAndCreateTimeAfter(Long uid,Integer status,Long createTime);
}
