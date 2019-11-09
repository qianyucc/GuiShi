package com.qianyu.wxapp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.extra.mail.MailUtil;
import com.qianyu.wxapp.entity.Letter;
import com.qianyu.wxapp.repository.LetterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxappApplicationTests {

    @Autowired
    private LetterRepository letterRepository;

    @Test
    public void contextLoads() {
        //MailUtil.send("1413979079@qq.com", "标题", "<h1>这里是内容</h1>", true);
        //CronUtil.start();
        List<Letter> letterList = letterRepository.findAll();
        System.out.println("letterList = " + letterList);
    }

}
