package com.qianyucc.guishi.model.vo;

import cn.hutool.core.bean.*;
import cn.hutool.core.date.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.utils.*;
import lombok.*;

import java.util.*;

import static com.qianyucc.guishi.config.Constant.DATE_PATTERN;

/**
 * @author lijing
 * @date 2019-10-30 23:11
 * @description
 */
@Data
public class CountDownVO {
    public static CountDownVO create(CountDownDO countDownDO){
        CountDownVO countDownVO = new CountDownVO();
        BeanUtil.copyProperties(countDownDO, countDownVO);
        countDownVO.setEndTime(GlobalUtil.formatDate(countDownDO.getEndTime(), DATE_PATTERN));
        Date endDate = new Date(countDownDO.getEndTime());
        long betweenDay = DateUtil.between(new Date(), endDate, DateUnit.DAY) + 1;
        if (new Date().after(endDate)) {
            countDownVO.setStatus(-1);
        } else {
            countDownVO.setStatus(Math.toIntExact(betweenDay));
        }
        return countDownVO;
    }
    private Long id;
    private String event;
    private String endTime;
    private Integer status;
}
