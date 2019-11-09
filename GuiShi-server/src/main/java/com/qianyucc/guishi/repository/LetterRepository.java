package com.qianyucc.guishi.repository;

import com.qianyucc.guishi.model.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * @author lijing
 * @date 2019-04-24-19:33
 * @discroption 信件的数据库访问层
 */
public interface LetterRepository extends JpaRepository<LetterDO, Integer> {
}
