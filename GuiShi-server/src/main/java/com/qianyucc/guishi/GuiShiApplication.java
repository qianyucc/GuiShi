package com.qianyucc.guishi;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.scheduling.annotation.*;

/**
 * @author lijing
 * @date 2019-10-11 9:56
 * @description SpringBoot启动类
 */
@SpringBootApplication
@EnableScheduling // 启动定时任务
public class GuiShiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GuiShiApplication.class,args);
    }
}
