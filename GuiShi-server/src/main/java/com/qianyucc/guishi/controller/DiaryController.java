package com.qianyucc.guishi.controller;

import cn.hutool.core.io.*;
import com.google.common.base.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.model.vo.*;
import com.qianyucc.guishi.repository.*;
import com.qianyucc.guishi.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.*;
import java.io.*;
import java.time.*;
import java.util.*;
import java.util.Optional;

/**
 * @author lijing
 * @date 2019-11-02 10:37
 * @description
 */
@RestController
@RequestMapping("/api/diary")
public class DiaryController {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private DiaryService diaryService;
    @Value("${sources.images.path}")
    private String imagesDir;

    @PostMapping("/upload")
    public RespDataVO upload(MultipartFile file, Long uid, String content, Long timestamp) {
        String name = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + name.substring(name.lastIndexOf("."));
        String fullFilePath = imagesDir + fileName;
        try {
            FileUtil.writeFromStream(file.getInputStream(), fullFilePath);
            diaryService.ins(uid, content, timestamp, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespDataVO.ok("文件上传成功！");
    }

    @GetMapping("/upload")
    public RespDataVO getUpload(Long uid, String content, Long timestamp) {
        diaryService.ins(uid, content, timestamp, "");
        return RespDataVO.ok("日记发布成功！");
    }

    @GetMapping("/get")
    public List<DiaryVO> get(Long uid) {
        List<DiaryVO> diaryVOS = diaryService.find(uid);
        return diaryVOS;
    }

    @DeleteMapping("/del/{id}")
    public RespDataVO del(@PathVariable("id") Long id) {
        diaryRepository.deleteById(id);
        return RespDataVO.ok("删除成功！");
    }
}
