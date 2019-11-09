package com.qianyu.wxapp;

import cn.hutool.core.date.DateUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.extra.mail.MailUtil;
import com.qianyu.wxapp.entity.Letter;
import com.qianyu.wxapp.repository.LetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijing
 * @date 2019-04-29-20:26
 * @discroption spring-boot初始化
 */
@Component
public class WxApplicationRunner implements CommandLineRunner {

    @Autowired
    private LetterRepository letterRepository;

    @Override
    public void run(String... args) throws Exception {
        // 24小时检查一次
        CronUtil.schedule("0 8 * * *", new Task() {
            @Override
            public void execute() {
                List<Letter> list = getLetterNotPost();
                for (Letter letter : list) {
                    MailUtil.send(letter.getMail(), letter.getTitle(), getContent(letter), true);
                    System.out.println("邮件发送至:"+letter.getMail());
                }
            }
        });
        CronUtil.start();
    }


    /**
     * 生成内容html
     *
     * @param letter
     * @return
     */
    private String getContent(Letter letter) {
        return "<div style=\"background: #fff; border: 1px solid #ccc; margin: 2%; padding: 10px 30px\">" +
                "        <p style=\"margin: 0; padding: 0; font-size: 14px; line-height: 30px;text-align: center; color: #333; font-family: arial, sans-serif; font-weight: bold\">" +
                letter.getTitle() +
                "                </p>" +
                "        <p style=\"margin: 0; padding: 0; line-height: 30px; font-size: 14px; color: #333; font-family: '宋体', arial, sans-serif\">" +
                "            <span>" + letter.getContent() + "</span>" +
                "        </p>" +
                "        <div style=\"line-height: 80px; height: 80px\">&nbsp;</div>" +
                "        <p style=\"margin: 0; padding: 0; line-height: 30px; font-size: 14px; text-align:right;color: #333; font-family: '宋体', arial, sans-serif\">" +
                "                " + DateUtil.format(letter.getCreateTime(), "yyyy年MM月dd日") + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                "        </p>" +
                "    </div>";
    }

    /**
     * 找到当天需要通知的信件集合
     *
     * @return
     */
    public List<Letter> getLetterNotPost() {
        ArrayList<Letter> res = new ArrayList<>();
        List<Letter> letterList = letterRepository.findAll();
        for (Letter letter : letterList) {
            if (DateUtil.format(letter.getNoticeTime(), "yyyy-MM-dd").equals(DateUtil.today())) {
                res.add(letter);
            }
        }
        return res;
    }
}
