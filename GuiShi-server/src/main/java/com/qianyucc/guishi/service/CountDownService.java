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

import static com.qianyucc.guishi.config.Constant.*;

/**
 * @author lijing
 * @date 2019-10-30 22:40
 * @description
 */
@Service
public class CountDownService {
    @Autowired
    private CountDownRepository countDownRepository;

    public void insOrUpd(CountDownDTO countDownDTO) {
        if (countDownDTO.getId() == null) {
            CountDownDO countDownDO = new CountDownDO();
            BeanUtil.copyProperties(countDownDTO, countDownDO, CopyOptions.create().ignoreError());
            countDownDO.setStatus(0);
            countDownDO.setCreateTime(Instant.now().toEpochMilli());
            countDownDO.setEndTime(GlobalUtil.parseDate(countDownDTO.getEndTime(), DATE_PATTERN));
            countDownRepository.save(countDownDO);
        } else {
            countDownRepository.findById(countDownDTO.getId()).ifPresent(countDown -> {
                BeanUtil.copyProperties(countDownDTO, countDown, CopyOptions.create().ignoreError());
                countDown.setEndTime(GlobalUtil.parseDate(countDownDTO.getEndTime(), DATE_PATTERN));
                countDownRepository.save(countDown);
            });
        }

    }

    public List<CountDownVO> findByUid(Long uid) {
        List<CountDownDO> countDownDOS = countDownRepository.findByUid(uid);
        return countDownDOS
                .stream()
                .sorted(Comparator.comparing(CountDownDO::getEndTime))
                .map(countDownDO -> CountDownVO.create(countDownDO))
                .collect(Collectors.toList());
    }


    public CountDownVO findById(Long id) {
        return countDownRepository.findById(id)
                .map(countDownDO -> CountDownVO.create(countDownDO))
                .orElse(null);
    }

    public void upd(Long id, Integer status) {
        countDownRepository.findById(id)
                .ifPresent(countDownDO -> {
                    countDownDO.setStatus(status);
                    countDownRepository.save(countDownDO);
                });
    }
}
