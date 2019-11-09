package com.qianyu.wxapp.repository;

import com.qianyu.wxapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lijing
 * @date 2019-04-20-18:29
 * @discroption 用户类的数据库访问层
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 根据openid查询User信息
     * @param openid
     * @return
     */
    public List<User> findByOpenId(String openid);
}
