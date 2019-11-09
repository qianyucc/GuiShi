package com.qianyucc.guishi.repository;

import com.qianyucc.guishi.model.entity.*;
import org.springframework.data.jpa.repository.*;

/**
 * @author lijing
 * @date 2019-04-24-19:35
 * @discroption 日记的数据库访问层
 */
public interface DiaryRepository extends JpaRepository<DiaryDO, Long> {
}
