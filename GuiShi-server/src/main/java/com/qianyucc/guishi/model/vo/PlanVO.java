package com.qianyucc.guishi.model.vo;

import cn.hutool.core.bean.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.utils.*;
import lombok.*;

import static com.qianyucc.guishi.config.Constant.DATE_PATTERN;
import static com.qianyucc.guishi.config.Constant.TIME_PATTERN;

/**
 * @author lijing
 * @date 2019-10-30 23:22
 * @description
 */
@Data
public class PlanVO {
    public static PlanVO create(PlanDO planDO){
        PlanVO planVO = new PlanVO();
        BeanUtil.copyProperties(planDO, planVO);
        planVO.setStartTime(GlobalUtil.formatDate(planDO.getStartTime(), TIME_PATTERN));
        planVO.setEndTime(GlobalUtil.formatDate(planDO.getEndTime(), TIME_PATTERN));
        planVO.setCreateDate(GlobalUtil.formatDate(planDO.getCreateTime(), DATE_PATTERN));
        return planVO;
    }

    private Long id;
    private String content;
    private String startTime;
    private String endTime;

    private String createDate;
    private Integer status;
}
