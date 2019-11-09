package com.qianyucc.guishi.controller;

import com.qianyucc.guishi.model.dto.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.model.vo.*;
import com.qianyucc.guishi.repository.*;
import com.qianyucc.guishi.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author lijing
 * @date 2019-10-31 14:17
 * @description
 */
@RestController
@RequestMapping("/api/letter")
public class LetterController {
    @Autowired
    private LetterService letterService;
    @Autowired
    private LetterRepository letterRepository;

    @PostMapping("/add")
    public RespDataVO add(@RequestBody LetterDTO letterDTO) {
        letterService.ins(letterDTO);
        return RespDataVO.ok("插入成功！");
    }
    @GetMapping("/get")
    public List<LetterDO> add() {
        return letterRepository.findAll();
    }
}
