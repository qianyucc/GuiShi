package com.qianyucc.guishi.service;

import cn.hutool.core.bean.*;
import cn.hutool.core.bean.copier.*;
import cn.hutool.core.date.*;
import com.qianyucc.guishi.model.dto.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.model.vo.*;
import com.qianyucc.guishi.repository.*;
import com.qianyucc.guishi.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

import static com.qianyucc.guishi.config.Constant.DATE_PATTERN;
import static com.qianyucc.guishi.config.Constant.TIME_PATTERN;

/**
 * @author lijing
 * @date 2019-10-30 22:48
 * @description
 */
@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public void insOrUpd(PlanDTO planDTO) {
        if (planDTO.getId() == null) {
            PlanDO planDO = new PlanDO();
            BeanUtil.copyProperties(planDTO, planDO, CopyOptions.create().ignoreError());
            planDO.setCreateTime(Instant.now().toEpochMilli());
            planDO.setStatus(0);
            planDO.setStartTime(GlobalUtil.parseDate(planDTO.getStartTime(), TIME_PATTERN));
            planDO.setEndTime(GlobalUtil.parseDate(planDTO.getEndTime(), TIME_PATTERN));
            planRepository.save(planDO);
        } else {
            planRepository.findById(planDTO.getId())
                    .ifPresent(plan -> {
                        BeanUtil.copyProperties(planDTO, plan);
                        plan.setStartTime(GlobalUtil.parseDate(planDTO.getStartTime(), TIME_PATTERN));
                        plan.setEndTime(GlobalUtil.parseDate(planDTO.getEndTime(), TIME_PATTERN));
                        planRepository.save(plan);
                    });
        }

    }

    public List<PlanVO> find(Long uid, boolean isAll) {
        List<PlanDO> planDOS;
        if (isAll) {
            planDOS = planRepository.findByUid(uid);
            planDOS.sort(Comparator.comparing(PlanDO::getCreateTime).reversed());
        } else {
            planDOS = planRepository.findByUidAndCreateTimeAfter(uid, GlobalUtil.getCurrentDateMillions());
            planDOS.sort(Comparator.comparing(PlanDO::getStartTime));
        }
        planDOS.forEach(planDO -> {
            if (planDO.getEndTime() - GlobalUtil.getCurrentTimeMillions() <= 0 && planDO.getStatus() == 0) {
                planDO.setStatus(-1);
            }
        });
        return dosToVos(planDOS);
    }

    private List<PlanVO> dosToVos(List<PlanDO> planDOS) {
        return planDOS
                .stream()
                .map(planDO -> PlanVO.create(planDO))
                .collect(Collectors.toList());
    }

    public void upd(Long id, Integer status) {
        planRepository.findById(id)
                .ifPresent(planDO -> {
                    planDO.setStatus(status);
                    planRepository.save(planDO);
                });
    }

    public PlanVO findById(Long id) {
        return planRepository.findById(id)
                .map(planDO -> PlanVO.create(planDO))
                .orElse(null);
    }
}
