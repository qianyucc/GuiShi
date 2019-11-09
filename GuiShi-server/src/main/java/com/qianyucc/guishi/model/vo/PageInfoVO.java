package com.qianyucc.guishi.model.vo;

import lombok.*;

import java.util.*;

/**
 * @author lijing
 * @date 2019-10-12 14:34
 * @description 储存分页信息
 */
@Data
public class PageInfoVO<T> {
    private List<T> data;
    private Integer totalPages;
    private Integer currentPage;
    private Long totalCount;
}
