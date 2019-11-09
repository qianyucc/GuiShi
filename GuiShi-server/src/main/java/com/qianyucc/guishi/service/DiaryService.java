package com.qianyucc.guishi.service;

import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.model.vo.*;
import com.qianyucc.guishi.repository.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

/**
 * @author lijing
 * @date 2019-11-02 12:45
 * @description
 */
@Slf4j
@Service
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;
    @Value("${sources.images.uri}")
    public String IMAGE_URI;

    public List<DiaryVO> find(Long uid) {
        DiaryDO diaryDO = new DiaryDO();
        diaryDO.setUid(uid);
        List<DiaryDO> diaryDOS = diaryRepository.findAll(Example.of(diaryDO));
        return diaryDOS.stream()
                .map(diary -> DiaryVO.create(diary, IMAGE_URI))
                .collect(Collectors.toList());
    }

    public synchronized void ins(Long uid, String content, Long timestamp, String fileName) {
        DiaryDO diaryDO = new DiaryDO();
        diaryDO.setUid(uid);
        diaryDO.setCreateTime(timestamp);
        diaryDO.setContent(content);

        DiaryDO dirayExample = new DiaryDO();
        dirayExample.setCreateTime(timestamp);
        Optional<DiaryDO> one = diaryRepository.findOne(Example.of(dirayExample));
        if (one.isPresent()) {
            DiaryDO diary = one.get();
            diary.setImageUrls(diary.getImageUrls() + "," + fileName);
            diaryRepository.save(diary);
        } else {
            diaryDO.setImageUrls(fileName);
            diaryRepository.save(diaryDO);
        }
    }
}
