package com.qianyucc.guishi.controller;

import com.qianyucc.guishi.model.dto.*;
import com.qianyucc.guishi.model.vo.*;
import com.qianyucc.guishi.repository.*;
import com.qianyucc.guishi.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author lijing
 * @date 2019-10-30 22:48
 * @description
 */
@RestController
@RequestMapping("/api/plan")
public class PlanController {
    @Autowired
    private PlanService planService;
    @Autowired
    private PlanRepository planRepository;

    @PostMapping("/aou")
    public RespDataVO addOrUpd(@RequestBody PlanDTO planDTO) {
        planService.insOrUpd(planDTO);
        return RespDataVO.ok("插入成功！");
    }

    @GetMapping("/upd")
    public RespDataVO upd(Long id, Integer status) {
        planService.upd(id, status);
        return RespDataVO.ok("更新成功！");
    }

    @GetMapping("/get")
    public List<PlanVO> get(Long uid, @RequestParam(required = false, defaultValue = "false") boolean isAll) {
        List<PlanVO> planVOS = planService.find(uid, isAll);
        return planVOS;
    }

    @GetMapping("/getById")
    public PlanVO getById(Long id) {
        return planService.findById(id);

    }

    @DeleteMapping("/del/{id}")
    public RespDataVO del(@PathVariable("id") Long id) {
        planRepository.deleteById(id);
        return RespDataVO.ok("删除成功！");
    }
}
