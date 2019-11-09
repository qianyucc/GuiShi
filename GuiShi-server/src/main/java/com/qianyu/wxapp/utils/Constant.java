package com.qianyu.wxapp.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lijing
 * @date 2019-04-26-15:55
 * @discroption 要用到的常量
 */
public class Constant {
    // 当参数不完整时，返回的数据
    public static final HashMap<String, Object> paraErrorMap;
    // 当参数无效时返回的数据
    public static final HashMap<String, Object> paraInvalidMap;
    // 信息删除成功时返回的map
    public static final HashMap<String, Object> deleteSuccessMap;
    // 同一用户插入重复信息时候返回的map
    public static final HashMap<String, Object> itemRepeatMap;
    // 自定义日期排序器
    public static final Comparator<String> dateComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            DateTime date1 = DateUtil.parse(o1);
            DateTime date2 = DateUtil.parse(o2);
            return -date1.compareTo(date2);
        }
    };

    // 信息更新成功后返回的map
    public static Map<String, Object> getUpdateSuccessMap(Object save) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("error_code", 0);
        map.put("data", save);
        return map;
    }

    static {
        paraErrorMap = new HashMap<>();
        paraErrorMap.put("error_code", 1001);
        paraErrorMap.put("error_msg", "参数不完整，请检查参数个数以及参数形式。");

        paraInvalidMap = new HashMap<>();
        paraInvalidMap.put("error_code", 1003);
        paraInvalidMap.put("error_msg", "传入的无效参数，请检查参数规范。");

        deleteSuccessMap = new HashMap<>();
        deleteSuccessMap.put("error_code", 0);
        deleteSuccessMap.put("error_msg", "删除成功！");

        itemRepeatMap = new HashMap<>();
        itemRepeatMap.put("error_code", 1004);
        itemRepeatMap.put("error_msg", "插入数据重复！");
    }
}
