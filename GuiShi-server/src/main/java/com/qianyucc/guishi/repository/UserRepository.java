package com.qianyucc.guishi.repository;

import com.qianyucc.guishi.model.entity.*;
import org.springframework.data.jpa.repository.*;

/**
 * @author lijing
 * @date 2019-04-20-18:29
 * @discroption 用户类的数据库访问层
 */
public interface UserRepository extends JpaRepository<UserDO,Long> {
}
