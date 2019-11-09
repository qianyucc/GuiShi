package com.qianyucc.guishi.model.vo;

import com.google.common.base.*;
import com.qianyucc.guishi.config.*;
import com.qianyucc.guishi.model.entity.*;
import com.qianyucc.guishi.utils.*;
import lombok.*;

import java.util.*;
import java.util.stream.*;

import static com.qianyucc.guishi.config.Constant.*;

/**
 * @author lijing
 * @date 2019-11-02 12:35
 * @description
 */
@Data
public class DiaryVO {
    public static DiaryVO create(DiaryDO diaryDO, String imageUri) {
        DiaryVO diaryVO = new DiaryVO();
        diaryVO.setId(diaryDO.getId());
        diaryVO.setCreateTime(GlobalUtil.formatDate(diaryDO.getCreateTime(), DEFAULT_DATE_PATTERN));
        diaryVO.setContent(diaryDO.getContent());
        String imageUrls = diaryDO.getImageUrls();
        List<String> list = new ArrayList<>();
        if (!Strings.isNullOrEmpty(imageUrls)) {
            list = Arrays.stream(imageUrls.split(","))
                    .map(fileName -> imageUri + fileName)
                    .collect(Collectors.toList());
        }
        diaryVO.setImages(list);
        return diaryVO;
    }

    private Long id;
    private String createTime;
    private List<String> images;
    private String content;
}
