package com.qianyucc.guishi.controller;

import com.qianyucc.guishi.model.dto.*;
import com.qianyucc.guishi.model.vo.*;
import com.qianyucc.guishi.service.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author lijing
 * @date 2019-11-01 14:32
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public RespDataVO login(@RequestBody LoginDTO loginDTO) {
        UserVO userVO = userService.login(loginDTO);
        return RespDataVO.ok("登录成功！", userVO);
    }
    @GetMapping("/get")
    public UserVO get(Long id){
        return userService.find(id);
    }
}
