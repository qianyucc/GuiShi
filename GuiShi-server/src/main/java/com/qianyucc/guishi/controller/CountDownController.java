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
 * @date 2019-10-30 22:38
 * @description
 */
@RestController
@RequestMapping("/api/countdown")
public class CountDownController {
    @Autowired
    private CountDownService countDownService;
    @Autowired
    private CountDownRepository countDownRepository;

    @PostMapping("/aou")
    public RespDataVO addOrUpd(@RequestBody CountDownDTO countDownDTO) {
        countDownService.insOrUpd(countDownDTO);
        return RespDataVO.ok("插入成功！");
    }

    @GetMapping("/updStatus")
    public RespDataVO updStatus(Long id,Integer status){
        countDownService.upd(id,status);
        return RespDataVO.ok("更新成功！");
    }

    @GetMapping("/getAll")
    public List<CountDownVO> getAll(Long uid) {
        List<CountDownVO> countDownVOS = countDownService.findByUid(uid);
        return countDownVOS;
    }
    @GetMapping("/getById")
    public CountDownVO getById(Long id){
        return countDownService.findById(id);
    }

    @DeleteMapping("/del/{id}")
    public RespDataVO fel(@PathVariable(value = "id") Long id) {
        countDownRepository.deleteById(id);
        return RespDataVO.ok("删除成功！");
    }
}
