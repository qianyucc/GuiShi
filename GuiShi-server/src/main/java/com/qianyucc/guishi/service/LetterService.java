package com.qianyucc.guishi.service;

import cn.hutool.core.bean.*;
import cn.hutool.core.bean.copier.*;
import com.qianyucc.guishi.model.dto.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.repository.*;
import com.qianyucc.guishi.utils.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;

import static com.qianyucc.guishi.config.Constant.DATE_PATTERN;

/**
 * @author lijing
 * @date 2019-10-31 14:20
 * @description
 */
@Service
public class LetterService {
    @Autowired
    private LetterRepository letterRepository;

    public void ins(LetterDTO letterDTO) {
        LetterDO letterDO = new LetterDO();
        BeanUtil.copyProperties(letterDTO, letterDO, CopyOptions.create().ignoreError());
        letterDO.setNoticeTime(GlobalUtil.parseDate(letterDTO.getNoticeTime(), DATE_PATTERN));
        letterDO.setCreateTime(Instant.now().toEpochMilli());
        letterRepository.save(letterDO);
    }
}
