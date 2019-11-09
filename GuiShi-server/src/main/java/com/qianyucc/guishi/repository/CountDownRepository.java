package com.qianyucc.guishi.repository;

import com.qianyucc.guishi.model.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

/**
 * @author lijing
 * @date 2019-04-24-19:29
 * @discroption 倒计时的数据库访问层
 */
public interface CountDownRepository extends JpaRepository<CountDownDO, Long> {
    List<CountDownDO> findByUid(Long uid);
}
