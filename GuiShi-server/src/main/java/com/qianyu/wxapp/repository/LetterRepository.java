package com.qianyu.wxapp.repository;

import com.qianyu.wxapp.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author lijing
 * @date 2019-04-24-19:33
 * @discroption 信件的数据库访问层
 */
@Repository
public interface LetterRepository extends JpaRepository<Letter, Integer> {
    /**
     * 根据uid和createTime查询数据
     *
     * @param createTime
     * @param uid
     * @return
     */
    List<Letter> findByUidAndCreateTimeAfter(int uid, Date createTime);

    /**
     * 根据uid查询信件
     *
     * @param uid
     * @return
     */
    List<Letter> findByUid(int uid);

    /**
     * 查询是否有重复信件
     * @param uid
     * @param title
     * @param content
     * @param mail
     * @param noticeTime
     * @return
     */
    List<Letter> findByUidAndTitleAndContentAndMailAndNoticeTime(Integer uid,String title,String content,String mail,Date noticeTime);
}
