package com.qianyucc.guishi.global;

import cn.hutool.core.collection.*;
import cn.hutool.core.date.*;
import cn.hutool.extra.mail.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.repository.*;
import com.qianyucc.guishi.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

import static com.qianyucc.guishi.config.Constant.DATE_PATTERN;

/**
 * @author lijing
 * @date 2019-10-14 11:48
 * @description 规时小程序定时任务，每天八点准时发送邮件
 */
@Component
@Slf4j
public class GuiShiSchedule {
    @Autowired
    private LetterRepository letterRepository;
    private static MailAccount account;

    static {
        account = new MailAccount();
        account.setHost("smtp.163.com");
        account.setPort(25);
        //account.setAuth(true);
        account.setFrom("L1413979079@163.com");
        account.setUser("L1413979079");
        account.setPass("wangyi666");
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendEmail() {
        sendLetters();
    }

    /**
     * 生成邮件发送过程中需要的HTML
     *
     * @param letter
     * @return
     */
    private String getContent(LetterDO letter) {
        return "<div style=\"background: #fff; border: 1px solid #ccc; margin: 2%; padding: 10px 30px\">" +
                "        <p style=\"margin: 0; padding: 0; font-size: 14px; line-height: 30px;text-align: center; color: #333; font-family: arial, sans-serif; font-weight: bold\">" +
                letter.getTitle() +
                "                </p>" +
                "        <p style=\"margin: 0; padding: 0; line-height: 30px; font-size: 14px; color: #333; font-family: '宋体', arial, sans-serif\">" +
                "            <span>" + letter.getContent() + "</span>" +
                "        </p>" +
                "        <div style=\"line-height: 80px; height: 80px\">&nbsp;</div>" +
                "        <p style=\"margin: 0; padding: 0; line-height: 30px; font-size: 14px; text-align:right;color: #333; font-family: '宋体', arial, sans-serif\">" +
                "                " + GlobalUtil.formatDate(letter.getNoticeTime(), DATE_PATTERN) + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                "        </p>" +
                "    </div>";
    }

    /**
     * 找到需要今天发送的邮件
     *
     * @return
     */
    public void sendLetters() {
        letterRepository.findAll().stream()
                .filter(letterDO -> GlobalUtil.formatDate(letterDO.getNoticeTime(), DATE_PATTERN).equals(DateUtil.today()))
                .forEach(letterDO -> {
                    MailUtil.send(account, CollUtil.newArrayList(letterDO.getMail()), letterDO.getTitle(), getContent(letterDO), true);
                    log.info("邮件发送至:{}", letterDO.getMail());
                });
    }
}